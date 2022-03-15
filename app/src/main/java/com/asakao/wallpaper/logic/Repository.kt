package com.asakao.wallpaper.logic

import android.util.Log
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    fun getCategory() = fire(Dispatchers.IO) {
        val categoryResponse = WallPaperNetwork.getCategory()
        if (categoryResponse.msg == "success") {
            val category = categoryResponse.res.category
            Log.d("TAG", "getCategory: " + category)
            Result.success(category)
        } else {
            Result.failure(RuntimeException("there is nothing info"))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

}