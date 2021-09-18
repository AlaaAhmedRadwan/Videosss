package com.example.videosdownloader.presentaion.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.videosdownloader.R
import com.example.videosdownloader.presentaion.homefragment.HomeFragment

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.ViewModelProviders
import com.example.videosdownloader.helper.PreferenceHelper
import com.example.videosdownloader.presentaion.ClickHandler
import com.example.videosdownloader.presentaion.Permissions
import com.example.videosdownloader.presentaion.homefragment.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_items_adapter.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    var preferenceHelper: PreferenceHelper? = null

    val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceHelper = PreferenceHelper(this)

        if (Permissions().CheckPermission(this)) { }
        else { Permissions().RequestPermission(this) }

        val home = HomeFragment()
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0, 0)
            .replace(R.id.main_frame, home)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

      folder.setOnClickListener {  ClickHandler().OpenVideoFolder(this)}

    }




}