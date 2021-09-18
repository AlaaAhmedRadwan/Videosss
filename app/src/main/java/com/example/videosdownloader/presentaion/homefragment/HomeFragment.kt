package com.example.videosdownloader.presentaion.homefragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videosdownloader.R
import com.example.videosdownloader.databinding.HomeFragmentBinding
import com.example.videosdownloader.model.MainDataItem
import com.example.videosdownloader.presentaion.homefragment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import java.io.File
import java.io.IOException
import com.example.videosdownloader.helper.SUCCESS_MotionToast
import com.example.videosdownloader.presentaion.ClickHandler
import com.example.videosdownloader.presentaion.homefragment.viewmodel.BindableString
import com.example.videosdownloader.presentaion.mainactivity.MainActivity
import kotlinx.android.synthetic.main.main_items_adapter.view.*

import java.util.*
import kotlin.collections.ArrayList


open class HomeFragment : Fragment() {
    val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java) }

    lateinit var MainAdapter: Main_Adapter
    lateinit var list: ArrayList<MainDataItem>
    var data: MainDataItem? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: HomeFragmentBinding = DataBindingUtil.inflate(inflater,
                R.layout.home_fragment, container, false)

        viewModel.GetMainData(context as MainActivity)

      // viewModel.GetDataFromJson(requireContext())

        viewModel.MainDataLD?.observe(viewLifecycleOwner, Observer { it ->

                MainAdapter = Main_Adapter(viewModel, context, it)
                recyler.layoutManager = LinearLayoutManager(context)
                recyler.adapter = MainAdapter;
                stoploading()
        })





        viewModel.loadingLivedat.observe(viewLifecycleOwner,
            Observer { loading -> shimmer_view_container.setVisibility(if (loading!!) View.VISIBLE else View.GONE) })

        return view.root
    }


    override fun onResume() {
        super.onResume()
        shimmer_view_container.startShimmerAnimation() }

    override fun onPause() {
        shimmer_view_container.stopShimmerAnimation()
        super.onPause() }

    fun stoploading() {
        shimmer_view_container?.setVisibility(View.GONE)
        shimmer_view_container?.stopShimmerAnimation() }



}