package com.example.myapplication.model

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialImagelist = imageList(resources)
    private val imagesLiveData = MutableLiveData(initialImagelist)

    // initial list of images
    private fun imageList(resources: Resources): List<Image> {
        return listOf(Image("test3"), Image("test2"))
//        return imagesLiveData.value
    }

    fun getImageList():LiveData<List<Image>> {
        return imagesLiveData
    }

    fun getImageForId(id: String): Image? {
        imagesLiveData.value?.let { images ->
            return images.firstOrNull { it.fileName == id}
        }
        return null
    }

    fun addImage(image: Image) {
        val currentList = imagesLiveData.value
        if (currentList == null) {
            imagesLiveData.postValue(listOf(image))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, image)
            imagesLiveData.postValue(updatedList)
        }
    }
    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}