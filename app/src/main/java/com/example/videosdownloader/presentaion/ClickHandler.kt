package com.example.videosdownloader.presentaion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.Window
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.videosdownloader.R
import com.example.videosdownloader.model.MainDataItem
import com.example.videosdownloader.presentaion.mainactivity.MainActivity
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.example.videosdownloader.presentaion.homefragment.viewmodel.BindableString
import com.example.videosdownloader.presentaion.webview.WebViewActivity
import java.io.File



class ClickHandler {

    fun Download(data:MainDataItem,context: Context,binding:BindableString) {
         var pDialog: SweetAlertDialog
        pDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
        pDialog!!.setContentText("Confirm Download")
        pDialog!!.setConfirmText("Confirm")
        pDialog!!.setCancelText("Cancel")
        pDialog!!.show()
        pDialog.setCancelClickListener { pDialog.hide() }

        pDialog.setConfirmClickListener {
         (context as MainActivity).viewModel.DownLoadURL(data.id,data.url!!,context,binding)
            pDialog.hide()
        }

    }

    fun Open_PDF(context: Context,data: MainDataItem){

        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("int_value", data);

        context.startActivity(intent)


    }
    fun OpenVideoFolder(context: Context) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val uri = Uri.parse(
            Environment.getExternalStorageDirectory().path
                    + "/myFolder/"
        )
        intent.setDataAndType(uri, "video/*")
        context.startActivity(Intent.createChooser(intent, "Open folder"))
    }


}