package com.example.videosdownloader.presentaion.homefragment.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import java.util.*
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData


class BindableString : BaseObservable() {

     var value = ObservableField<Int>();
     var visibility = ObservableField<Int>();
     var downloaded_text_visibility = MutableLiveData<Int>()

init {
    visibility.set(8)
    downloaded_text_visibility = MutableLiveData()

}
    fun updateDownloadStatusVisibility(status: Int){
        downloaded_text_visibility.postValue(status)
    }

}