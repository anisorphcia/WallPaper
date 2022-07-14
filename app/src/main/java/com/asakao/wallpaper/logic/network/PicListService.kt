package com.asakao.wallpaper.logic.network

import android.util.Log
import com.asakao.wallpaper.logic.model.PicListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PicListService {

    @GET("vertical/category/{id}/vertical?order=new&adult=false&first=0")
    fun getPicList(@Path("id") id: String, @Query("skip") skip: String, @Query("limit") limit: String) : Call<PicListResponse>

}