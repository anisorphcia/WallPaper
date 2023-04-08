package com.asakao.wallpaper.logic.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class PicListResponse(val msg: String, val res: ResPic)

data class ResPic(val vertical: List<Vertical>) : Serializable {}

@Entity
data class Vertical(val img: String, val thumb: String){
    @PrimaryKey(autoGenerate = false)
    lateinit var id: String
}

data class BaseQuery(val id: String, val skip: Int, val limit: Int)