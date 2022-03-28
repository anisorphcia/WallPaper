package com.asakao.wallpaper.utils

import android.os.Environment
import android.text.TextUtils
import com.liulishuo.filedownloader.util.FileDownloadUtils
import java.io.File

object FileUtil {
    /**
     * 创建下载文件夹路径
     */
    fun createPath(url: String): String? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        return getDefaultSaveFilePath(url)
    }

    /**
     * 创建下载文件夹路径
     */
    fun getDefaultSaveFilePath(url: String): String {
        return FileDownloadUtils.generateFilePath(getDownloadPath(), FileDownloadUtils.generateFileName(url) + ".png")
    }

    /**
     * 创建下载文件夹路径
     */
    fun getDownloadPath(): String {
        val filePath = Environment.getExternalStorageDirectory().path + "/wallpaper/download"
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.path
    }

    /**
     * 查找是否存在图片文件
     */
    fun isExistsImage(url: String): Boolean {
        try {
            val filePath = getDownloadPath() + "/" + FileDownloadUtils.generateFileName(url) + ".png"
            val file = File(filePath)
            return file.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }
}