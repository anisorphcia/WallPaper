package com.asakao.wallpaper.logic.model

import android.os.Parcelable
import java.io.Serializable

data class PicListResponse (val msg: String, val res: ResPic)

data class ResPic(val vertical: List<Vertical>) : Serializable{}

data class Vertical(val img: String)