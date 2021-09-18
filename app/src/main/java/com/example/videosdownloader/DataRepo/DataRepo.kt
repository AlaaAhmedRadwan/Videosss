package com.example.videosdownloader.DataRepo

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.example.videosdownloader.datalayer.APIServices
import com.example.videosdownloader.datalayer.ApiClient
import com.example.videosdownloader.model.MainDataItem


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.IOException
import kotlin.concurrent.thread

import com.example.videosdownloader.presentaion.mainactivity.MainActivity

import com.example.videosdownloader.R
import com.example.videosdownloader.helper.PreferenceHelper
import com.example.videosdownloader.helper.SUCCESS_MotionToast
import com.example.videosdownloader.presentaion.homefragment.viewmodel.BindableString


class DataRepo {

    @SuppressLint("CheckResult")
    fun GetMainData(context: Context, livedata: MutableLiveData<List<MainDataItem>>?) {
        getServergetway().GetMainData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                 //  livedata?.postValue(books)
                    RetriveDataFromJson(context, livedata)

                },
                { error ->
                    RetriveDataFromJson(context, livedata)
                }
            )
    }



  ///////

    var downloading = false

    fun DownLoadFile(item_id: Int, url: String, context: Context, livedata: MutableLiveData<Float>, bindingstring: BindableString) {

        if (downloading == false) {
            bindingstring.visibility.set(1)

            Log.i("dataurl", url.split('/').last())

            var urlExtention = url.split('/').last()

            var mydownloadid: Long = 0

            var request = DownloadManager.Request(Uri.parse(url))
                .setTitle(urlExtention)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, urlExtention)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)

            mydownloadid = dm.enqueue(request)

            var br = object : BroadcastReceiver() {
                override fun onReceive(p0: Context, p1: Intent?) {

                    var id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                    if (id == mydownloadid)

                        PreferenceHelper.addItemtoDownLoaded(item_id)

                        SUCCESS_MotionToast("downloaded" , context as MainActivity)

                    downloading = false

                }

            }

            downloading = true

            thread {
                while (downloading) {
                    val query = DownloadManager.Query()

                    query.setFilterById(mydownloadid)

                    val cursor = dm.query(query)
                    if (cursor.moveToFirst()) {
                        val bytesDownloaded =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        val bytesTotal =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false
                            bindingstring.visibility.set(8)

                        }

                        val progress = ((bytesDownloaded * 100L) / bytesTotal)
                        bindingstring.value.set(progress.toInt())
                        cursor.close()
                        bindingstring.updateDownloadStatusVisibility(0)
                    }
                }
            }
            context.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }


  /// Get data From Json When Api false
    fun RetriveDataFromJson(context: Context, livedata: MutableLiveData<List<MainDataItem>>?) {

        val jsonFileString = getJsonDataFromAsset(context, "data.json")
        Log.i("data", jsonFileString!!)

        val gson = Gson()
        val listPersonType = object : TypeToken<List<MainDataItem>>() {}.type

        var persons: List<MainDataItem> = gson.fromJson(jsonFileString, listPersonType)
        livedata!!.postValue(persons)
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }



    fun getServergetway(): APIServices {
        return ApiClient.client!!.create(APIServices::class.java)
    }
}

