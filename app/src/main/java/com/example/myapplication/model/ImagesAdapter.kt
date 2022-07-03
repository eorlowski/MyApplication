package com.example.myapplication.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.io.InputStream

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
//            imageView.setImageURI(image.uri)

            if (image.uri !=  null) {
                val bitmap = decodeSampledBitmapFromResource(image.uri, 100, 100)
                imageView.setImageBitmap(bitmap)

            }
        }

        fun decodeSampledBitmapFromResource(
            uri: Uri,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap? {
            var inputStream = itemView.context.getContentResolver().openInputStream(uri)

            // First decode with inJustDecodeBounds=true to check dimensions
            return BitmapFactory.Options().run {
                inJustDecodeBounds = true
                BitmapFactory.decodeStream(inputStream, null, this)

                // Calculate inSampleSize
                inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

                // Decode bitmap with inSampleSize set
                inJustDecodeBounds = false
                inputStream = itemView.context.getContentResolver().openInputStream(uri)
                BitmapFactory.decodeStream(inputStream, null, this)
            }
//            inputStream?.close()
        }

        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val (height: Int, width: Int) = options.run { outHeight to outWidth }
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight: Int = height / 2
                val halfWidth: Int = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
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
