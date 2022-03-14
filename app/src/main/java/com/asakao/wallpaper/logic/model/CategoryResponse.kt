package com.asakao.wallpaper.logic.model

data class CategoryResponse(val msg: String, val res: Res) {

    data class Res(val category: List<Category>)

    data class Category(val ename: String, val name: String, val cover: String, val sn: Int, val nimgs: Int, val type: Int, val id: String, val desc: String)

}