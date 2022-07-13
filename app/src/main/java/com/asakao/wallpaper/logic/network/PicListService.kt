package com.asakao.wallpaper.logic.network

import com.asakao.wallpaper.logic.model.PicListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PicListService {

    @GET("vertical/category/{id}/vertical?order=new&adult=false&first=0&skip={skip}&limit={limit}")
    fun getPicList(@Path("id") id: String, @Path("skip") skip: Int, @Path("limit") limit: Int) : Call<PicListResponse>

}