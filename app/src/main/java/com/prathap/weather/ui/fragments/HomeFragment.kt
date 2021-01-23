package com.prathap.weather.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.prathap.weather.R
import com.prathap.weather.di.Injectable
import com.prathap.weather.models.WeatherForecast
import com.prathap.weather.ui.HomeViewModel
import com.prathap.weather.ui.bindings.HomeListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 *
 */
class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private val cityWeather = ArrayList<WeatherForecast>()
    val cityAdapter = HomeListAdapter(cityWeather)

//    val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_cities.layoutManager = LinearLayoutManager(activity)
        rv_list_cities.adapter = cityAdapter

        viewModel.dataResults.observe(viewLifecycleOwner, Observer { cityWeatherResults ->
            if (cityWeatherResults.success) {
                cityWeatherResults.data?.let { weatherForecast ->
                    cityWeather.clear()
                    cityWeather.addAll(weatherForecast.list)
                    cityAdapter.notifyDataSetChanged()
                    //(activity as FragmentNavigator).loadWeatherFragment(weatherForecast)
                    //  findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                } ?: run {
                    showError()
                }
            } else {
                showError()
            }
        })

        viewModel.bookmarks.observe(viewLifecycleOwner, Observer { bookmarks ->
            val cityIds: String = bookmarks.joinToString { it -> "${it.id}" }.replace(" ", "")
            viewModel.getBookMarkedData(cityIds)
        })

        viewModel.cityResults.observe(viewLifecycleOwner, Observer { cityWeather ->
            cityWeather?.data?.let {
                viewModel.addBookmarkToDB(it.id.toLong(), it.coord.lat.toString(), it.coord.lon.toString())
            }
        })


        arguments?.let {
            if (it.containsKey("location")) {
                val location: LatLng? = it.getParcelable("location")
                location?.let {
                    viewModel.getCityWeather(location.latitude, location.longitude)
                }
            }
        }
        Log.d("TAG", "location $arguments?.getParcelable<LatLng>(\"location\")")

    }


    private fun showError() {
        Toast.makeText(activity, "Something wrong!!!", Toast.LENGTH_SHORT).show()
    }
}