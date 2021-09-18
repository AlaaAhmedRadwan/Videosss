package com.example.videosdownloader.presentaion.homefragment.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.videosdownloader.DataRepo.DataRepo
import com.example.videosdownloader.model.MainDataItem

import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel : ViewModel(){

    var DateRepoCompnay: DataRepo = DataRepo()
    var mCompositeDisposable = CompositeDisposable()

    var errorLivedat: MutableLiveData<String> = MutableLiveData()

    var loadingLivedat: MutableLiveData<Boolean> = MutableLiveData()
    var MainDataLD: MutableLiveData<List<MainDataItem>>? = null
    var DownLoadedObject: MutableLiveData<Float> = MutableLiveData()

    init {
        errorLivedat = MutableLiveData()
        loadingLivedat = MutableLiveData()
        MainDataLD = MutableLiveData()
        DownLoadedObject = MutableLiveData()
        
    }


    fun GetMainData(context: Context) {
        DateRepoCompnay.GetMainData(context,MainDataLD)

    }

    fun DownLoadURL(id:Int,Url: String, context: Context, bindingstring: BindableString) {

        DateRepoCompnay.DownLoadFile(id,Url, context, DownLoadedObject,bindingstring)

    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
        mCompositeDisposable.clear()

    }
}