package com.example.videosdownloader.presentaion.homefragment

import android.content.Context
import android.media.MediaPlayer.OnCompletionListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videosdownloader.R
import com.example.videosdownloader.databinding.MainItemsAdapterBinding
import com.example.videosdownloader.helper.PreferenceHelper
import com.example.videosdownloader.model.MainDataItem
import com.example.videosdownloader.presentaion.ClickHandler
import com.example.videosdownloader.presentaion.mainactivity.MainActivity
import com.example.videosdownloader.presentaion.homefragment.viewmodel.MainViewModel
import java.util.ArrayList
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import com.example.videosdownloader.presentaion.homefragment.viewmodel.BindableString
import java.io.IOException


class Main_Adapter(
    var viewModel: MainViewModel, var context: Context?, var data: List<MainDataItem>
) : RecyclerView.Adapter<CustomViewHolder>() {

    private var item_id: ArrayList<String> = ArrayList<String>()
    var bindableString: BindableString?=null
    override fun getItemCount(): Int {
        return data.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        p0.bind(context, data.get(p1))

        try {
           /// Check if downloaded before
            item_id = PreferenceHelper.retriveDownloaded()!!
            if (!PreferenceHelper.retriveDownloaded().isNullOrEmpty()) {
                if (item_id.contains(p0.binding.data?.id.toString())) {
                    p0.binding.downloadedAlready.visibility = View.VISIBLE
                } else {
                    p0.binding.downloadedAlready.visibility = View.GONE
                }
            } else {
                p0.binding.downloadedAlready.visibility = View.GONE
            }

        } catch (e: Exception) {
        }

        p0.binding.data?.url!!.replace(")", "")

        if (data.get(p1).type == "VIDEO") {
                try {
                    val uri = Uri.parse(data.get(p1).url)
                    p0.binding.simpleVideoView.setVideoURI(uri);
                    p0.binding.progressBar.setVisibility(View.GONE)
                    p0.binding.simpleVideoView.start()

                    Handler(Looper.getMainLooper()).postDelayed({
                        p0.binding.simpleVideoView.pause()
                        p0.binding.play.visibility = View.VISIBLE }, 3500)

                    p0.binding.play.setOnClickListener {
                        p0.binding.simpleVideoView.start()
                        p0.binding.play.visibility = View.GONE
                    }


                } catch (e: IOException) {
                    // presumably an exception is thrown the first time it gets 404 so put the error handling code here
                }



                p0.binding.simpleVideoView
                    .setOnCompletionListener(OnCompletionListener { mp ->
                        p0.binding.play.visibility = View.VISIBLE


                    })



        }else{
            p0.binding.progressBar.setVisibility(View.GONE)

        }

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val binding: MainItemsAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.main_items_adapter, p0, false
        )

        return CustomViewHolder(binding)

    }


}


class CustomViewHolder(
     val binding: MainItemsAdapterBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(context: Context?, data: MainDataItem) {

        binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
        binding.bindingadater = BindableString()
    }


}
