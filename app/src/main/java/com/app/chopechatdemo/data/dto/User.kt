package com.app.chopechatdemo.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val mobileNumber: String,
    val name: String,
    val lastMessage: String = "",
    val channelId: String = "",
) : Parcelable
