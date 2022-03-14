package com.asakao.wallpaper.logic.network

import android.util.Log
import com.asakao.wallpaper.logic.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface WallPaperService {

    @GET("vertical/category")
    fun getCategory(): Call<CategoryResponse>

}