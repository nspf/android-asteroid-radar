package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.api.NasaApiStatus
import com.udacity.asteroidradar.main.AsteroidAdapter
import com.udacity.asteroidradar.model.Asteroid


/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(data)
}


@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.placeholder_picture_of_day)
        .into(view)
}

@BindingAdapter("nasaApiStatus")
fun bindStatus(progressBar: ProgressBar, status: NasaApiStatus?) {

    val context = progressBar.context

    when (status) {
        NasaApiStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        NasaApiStatus.ERROR -> {
            Toast.makeText(context, context.getString(R.string.error_api_request), Toast.LENGTH_LONG)
                .show()
            progressBar.visibility = View.GONE

        }
        NasaApiStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
    }
} 
