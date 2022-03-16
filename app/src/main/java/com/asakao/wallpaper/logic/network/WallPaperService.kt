package com.asakao.wallpaper.logic.network

import com.asakao.wallpaper.logic.model.CategoryResponse
import com.asakao.wallpaper.logic.model.PicListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WallPaperService {

    @GET("vertical/category")
    fun getCategory(): Call<CategoryResponse>

}