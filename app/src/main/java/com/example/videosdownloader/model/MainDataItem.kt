package com.example.videosdownloader.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class MainDataItem(
    var id: Int,
    var name: String ? = null,
    var type: String? = null,
    var url: String? = null
) : Parcelable {

}