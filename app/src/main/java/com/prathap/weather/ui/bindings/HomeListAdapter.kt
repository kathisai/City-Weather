package com.prathap.weather.ui.bindings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prathap.weather.R
import com.prathap.weather.models.WeatherForecast
import com.prathap.weather.utils.IconUtil
import com.prathap.weather.utils.toCelsius
import kotlinx.android.synthetic.main.list_item_home_city.view.*

class HomeListAdapter(private val cityWeather: ArrayList<WeatherForecast>) :
        RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {
    var onItemClick: ((WeatherForecast) -> Unit)? = null

    /**
     * user inner to defind inner calls in parent class
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weatherForecast: WeatherForecast) {
            itemView.tvCityName.text = weatherForecast.name
            itemView.tvCurrentTemp.text = weatherForecast.main.temp.toCelsius()
            IconUtil.setWeatherIcon(itemView.imageViewCardWeatherIcon.context, itemView.imageViewCardWeatherIcon, weatherForecast.weather.last().id)
            itemView.setOnClickListener {
                onItemClick?.invoke(weatherForecast)
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_home_city, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(cityWeather[position])

    }

    override fun getItemCount() = cityWeather.size

    fun removeAt(position: Int) {
        cityWeather.removeAt(position)
        notifyItemRemoved(position)
    }
}
