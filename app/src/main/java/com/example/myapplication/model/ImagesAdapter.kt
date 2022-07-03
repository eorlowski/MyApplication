package com.example.myapplication.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ImagesAdapter(private val onClick: (Image) -> Unit): ListAdapter<Image, ImagesAdapter.ImageViewHolder>(ImageDiffFallback) {

    class ImageViewHolder(itemView: View, val onClick: (Image) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageTextView: TextView = itemView.findViewById(R.id.imageName)
        private val imageView: ImageView = itemView.findViewById(R.id.myimage)
        private var currentImage: Image? = null

        init {
            itemView.setOnClickListener{
                currentImage?.let {
                    onClick(it)
                }
            }
        }
        fun bind(image: Image) {
            currentImage = image
            imageTextView.text = image.fileName
            imageView.setImageURI(image.uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }
}

object ImageDiffFallback: DiffUtil.ItemCallback<Image>() {
    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}
