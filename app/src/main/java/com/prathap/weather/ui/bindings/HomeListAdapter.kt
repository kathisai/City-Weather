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
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
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

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_home_city, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bind(cityWeather[position])

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = cityWeather.size

    fun removeAt(position: Int) {
        cityWeather.removeAt(position)
        notifyItemRemoved(position)
    }

    //    https://gist.github.com/webserveis/aac47fc8ff97b69b52417a68fc6d7101
    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_ITEM = 1
    }

}
