package com.prathap.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.prathap.weather.R
import com.prathap.weather.di.Injectable
import com.prathap.weather.models.WeatherForecast
import com.prathap.weather.ui.HomeViewModel
import com.prathap.weather.ui.bindings.HomeListAdapter
import com.prathap.weather.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.loading_view.*
import javax.inject.Inject


/**
 * Initial Fragment to show list of bookmarked cities
 */
class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private val cityWeather = ArrayList<WeatherForecast>()
    val cityAdapter = HomeListAdapter(cityWeather)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_MapFragment)
        }
        context?.let {

            val swipeHandler = object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.removeBookmarkFromDB(cityWeather[viewHolder.adapterPosition].id.toLong())
                    cityAdapter.removeAt(viewHolder.adapterPosition)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(rv_list_cities)
        }

        rv_list_cities.layoutManager = LinearLayoutManager(activity)
        rv_list_cities.adapter = cityAdapter

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            loadingView.visibility = it
        })
        viewModel.dataResults.observe(viewLifecycleOwner, Observer { cityWeatherResults ->
            if (cityWeatherResults.success) {
                cityWeatherResults.data?.let { weatherForecast ->
                    cityWeather.clear()
                    cityWeather.addAll(weatherForecast.list)
                    cityAdapter.notifyDataSetChanged()
                    emptyView.visibility = View.GONE
                } ?: run {
                    showError()
                }
            } else {
                showEmptyView()
            }
        })

        viewModel.bookmarks.observe(viewLifecycleOwner, Observer { bookmarks ->
            if (bookmarks.isNotEmpty()) {
                val cityIds: String = bookmarks.joinToString { it -> "${it.id}" }.replace(" ", "")
                viewModel.getBookMarkedData(cityIds)
            } else {
                showEmptyView()
            }
        })

        viewModel.cityResults.observe(viewLifecycleOwner, Observer { cityWeather ->
            cityWeather?.data?.let {
                viewModel.addBookmarkToDB(it.id.toLong(), it.coord.lat.toString(), it.coord.lon.toString())
            }
        })

        cityAdapter.onItemClick = { city_weather ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(LatLng(city_weather.coord.lat, city_weather.coord.lon))
            findNavController().navigate(action)
        }

        arguments?.let {
            //TODO replace with constant
            if (it.containsKey("location")) {
                val location: LatLng? = it.getParcelable("location")
                location?.let {
                    viewModel.getCityWeather(location.latitude, location.longitude)
                }
            }
        }

    }

    private fun showError() {
        context?.let {
            Toast.makeText(it, getString(R.string.genrial_error_message), Toast.LENGTH_SHORT).show()
        }

    }

    private fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }
}