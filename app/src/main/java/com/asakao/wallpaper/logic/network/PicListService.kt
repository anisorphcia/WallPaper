package com.asakao.wallpaper.logic.network

import com.asakao.wallpaper.logic.model.PicListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PicListService {

    @GET("vertical/category/{id}/vertical?limit=30")
    fun getPicList(@Path("id") id: String) : Call<PicListResponse>

}