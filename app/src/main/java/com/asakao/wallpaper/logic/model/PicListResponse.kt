package com.asakao.wallpaper.logic.model

data class PicListResponse (val msg: String, val res: ResPic)

data class ResPic(val vertical: List<Vertical>)

data class Vertical(val img: String)