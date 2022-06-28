package com.example.myapplication.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImageListViewModel(val dataSource: DataSource) : ViewModel(){
    val imageLiveData = dataSource.getImageList()

    fun getImageForId(fileName: String) : Image? {
        return dataSource.getImageForId(fileName)
    }

    fun addImage(image: Image) {
        dataSource.addImage(image)
    }
}

class ImageListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return ImageListViewModel(dataSource = DataSource.getDataSource(context.resources)) as T
    }
}