package com.example.chuibbo_android.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageSliderAdapter(val context: Context, private val sliderImage: Array<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageSlider: ImageView = itemView.findViewById(R.id.imageSlider)

        fun bindImage(imageURL: String) {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(imageURL)
                }
                imageSlider.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_banner, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }
}
