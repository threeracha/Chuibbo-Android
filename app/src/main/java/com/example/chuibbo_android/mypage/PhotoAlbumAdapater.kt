package com.example.chuibbo_android.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chuibbo_android.R
import com.example.chuibbo_android.home.ImageLoader
import kotlinx.android.synthetic.main.mypage_album_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoAlbumAdapter(private val onClick: (PhotoAlbum) -> Unit) :
    ListAdapter<PhotoAlbum, PhotoAlbumAdapter.PhotoAlbumViewHolder>(PhotoAlbumDiffCallback) {

    /* ViewHolder for PhotoAlbum, takes in the inflated view and the onClick behavior. */
    class PhotoAlbumViewHolder(itemView: View, val onClick: (PhotoAlbum) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val resumePhoto: ImageView = itemView.resume_photo
        private var currentPhotoAlbum: PhotoAlbum? = null

        init {
            itemView.setOnClickListener {
                currentPhotoAlbum?.let {
                    onClick(it)
                }
            }
        }

        /* Bind photoAlbum name and image. */
        fun bind(photoAlbum: PhotoAlbum) {
            currentPhotoAlbum = photoAlbum

            if (photoAlbum.image != null) {

                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        ImageLoader.loadImage(photoAlbum.image)
                    }
                    resumePhoto.setImageBitmap(bitmap)
                }
            }
        }
    }

    /* Creates and inflates view and return PhotoAlbumViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mypage_album_item, parent, false)
        return PhotoAlbumViewHolder(view, onClick)
    }

    /* Gets current photoAlbum and uses it to bind view. */
    override fun onBindViewHolder(holder: PhotoAlbumViewHolder, position: Int) {
        val photoAlbum = getItem(position)
        holder.bind(photoAlbum)
    }
}

object PhotoAlbumDiffCallback : DiffUtil.ItemCallback<PhotoAlbum>() {
    override fun areItemsTheSame(oldItem: PhotoAlbum, newItem: PhotoAlbum): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PhotoAlbum, newItem: PhotoAlbum): Boolean {
        return oldItem.id == newItem.id
    }
}
