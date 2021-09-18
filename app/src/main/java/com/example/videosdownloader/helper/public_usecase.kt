package com.example.videosdownloader.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.example.videosdownloader.R
import com.example.videosdownloader.presentaion.mainactivity.MainActivity
import kotlinx.android.synthetic.main.home_fragment.*
import www.sanju.motiontoast.MotionToast

///TOAST_SUCCESS_MotionToast
fun SUCCESS_MotionToast(massage: String, context: Activity) {
    MotionToast.createColorToast(
        context,
    "downloaded!  😍",
        massage,
        MotionToast.TOAST_SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(context, R.font.helvetica_regular)
    )
}

@BindingAdapter("app:imageStock")

fun setimageStock(imageView: AppCompatImageView, resource: String?) {


    when (resource){
        "VIDEO" ->   imageView.setImageDrawable(
            ContextCompat.getDrawable(
                imageView.context!!, // Context
              R.drawable.downarrow // Drawable
            )
        )
        "PDF" ->   imageView.setImageDrawable(
            ContextCompat.getDrawable(
                imageView.context!!, // Context
               R.drawable.download_pdf// Drawable
            )
        )

    }

}

