package com.app.chopechatdemo.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Message(
    val id: String,
    val mobile: String,
    val toMobile : String,
    val name: String,
    val message: String,
    val createdAt: String,
) : Parcelable
