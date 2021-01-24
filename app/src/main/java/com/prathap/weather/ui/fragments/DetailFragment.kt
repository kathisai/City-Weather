package com.prathap.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.prathap.weather.R
import com.prathap.weather.di.Injectable
import com.prathap.weather.ui.HomeViewModel
import com.prathap.weather.utils.IconUtil
import com.prathap.weather.utils.toCelsius
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.loading_view.*
import javax.inject.Inject


class DetailFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        arguments?.let {
//            DetailFragmentArgs.fromBundle(it).selectedLocation.let { latLan ->
//                viewModel.getCityWeather(latLan.latitude, latLan.longitude)
//            }
//        }
        args.selectedLocation.let { latLan ->
            viewModel.getCityWeather(latLan.latitude, latLan.longitude)
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            loadingView.visibility = it

        })

        viewModel.cityResults.observe(viewLifecycleOwner, Observer { cityWeather ->
            cityWeather?.data?.let {
                cvWeatherCard.visibility = View.VISIBLE
                textViewCardCityName.text = it.name
                textViewCardWeatherDescription.text = it.weather.last().description
                textViewCardCurrentTemp.text = it.main.temp.toCelsius()
                context?.let { it1 -> IconUtil.setWeatherIcon(it1, imageViewCardWeatherIcon, it.weather.last().id) }
                textViewPressure.text = context?.getString(R.string.pressure)?.let { it1 -> String.format(it1, it.main.pressure.toString()) }
                textViewHumidity.text = context?.getString(R.string.humidity)?.let { it1 -> String.format(it1, it.main.humidity.toString()) }

            }
        })


    }


}