package com.example.videosdownloader.presentaion.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

import com.example.videosdownloader.R
import com.example.videosdownloader.presentaion.mainactivity.MainActivity


class SplashScreen : AppCompatActivity() {

    /** Duration of wait  */
    private val SPLASH_DISPLAY_LENGTH = 2000 //splash screen will be shown for 2 seconds

    /** Called when the activity is first created.  */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}