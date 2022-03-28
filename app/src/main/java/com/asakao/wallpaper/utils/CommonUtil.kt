package com.asakao.wallpaper.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.asakao.wallpaper.R
import com.google.android.material.snackbar.Snackbar

object CommonUtil {
    /**
     * 检查是否有网络
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info?.isAvailable!!
    }

    private fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * dip 转 px
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun showSnackBar(context: Context, view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        val mSnackBar: Snackbar = Snackbar.make(view, text, duration)
        mSnackBar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        mSnackBar.show()
    }

    fun toast(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, duration).show()
    }

}