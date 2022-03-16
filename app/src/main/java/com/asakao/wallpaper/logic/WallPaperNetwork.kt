package com.asakao.wallpaper.logic

import android.util.Log
import com.asakao.wallpaper.logic.network.PicListService
import com.asakao.wallpaper.logic.network.ServiceCreator
import com.asakao.wallpaper.logic.network.WallPaperService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WallPaperNetwork {

    private val wallPaperService = ServiceCreator.create(WallPaperService::class.java)

    suspend fun getCategory() = wallPaperService.getCategory().await()

    private val picListService = ServiceCreator.create(PicListService::class.java)

    suspend fun getPicList(id: String) = picListService.getPicList(id).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    Log.d("TAG", "onResponse: " + body)
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}