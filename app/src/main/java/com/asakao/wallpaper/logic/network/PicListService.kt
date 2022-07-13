package com.asakao.wallpaper.logic.network

import android.util.Log
import com.asakao.wallpaper.logic.model.PicListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PicListService {

    @GET("vertical/category/{id}/vertical?order=new&adult=false&first=0&skip={skip}&limit={limit}")
    fun getPicList(@Path("id") id: String, @Path("skip") skip: String, @Path("limit") limit: String) : Call<PicListResponse>

}