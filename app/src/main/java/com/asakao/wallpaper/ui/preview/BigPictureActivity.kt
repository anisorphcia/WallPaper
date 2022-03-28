package com.asakao.wallpaper.ui.preview

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.asakao.wallpaper.databinding.ActivityBigPictureBinding
import com.asakao.wallpaper.utils.CommonUtil
import com.asakao.wallpaper.utils.FileUtil
import com.asakao.wallpaper.widget.DownloadDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BigPictureActivity : AppCompatActivity() {

    lateinit var binding: ActivityBigPictureBinding
    lateinit var mRxPermissions: RxPermissions

//    val viewModel by lazy { ViewModelProvider(this).get(PicList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBigPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mRxPermissions = RxPermissions(this)

        val img = getIntent().getStringExtra("img");

        binding.ivBigPic.load(img)

        binding.ivDownload.setOnClickListener{
            if (img != null) {
                downloadImage(img)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun downloadImage(downloadUrl: String) {
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            .filter { aBoolean -> aBoolean }
//            .filter {
//                if (FileUtil.isExistsImage(downloadUrl)) {
//                    CommonUtil.showSnackBar(this, view,
//                        "文件已经下载了哦,到 ../wallpaper/download 查看吧")
//                    return@filter false
//                }
//                return@filter true
//            }
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val downloadDialog: DownloadDialog = DownloadDialog()
                val bundle: Bundle = Bundle()
                bundle.putString("downloadUrl", downloadUrl)
                downloadDialog.arguments = bundle
                downloadDialog.show(supportFragmentManager, "DownloadDialog")
            }, {
                CommonUtil.toast(this, "下载失败,请重试")
            })
    }

    fun saveBitmap2Gallery(context: Context, bitmap: Bitmap): Boolean {
        val name = System.currentTimeMillis().toString()
        val photoPath = Environment.DIRECTORY_DCIM + "/Camera"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, photoPath)//保存路径
            put(MediaStore.MediaColumns.IS_PENDING, true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //返回出一个URI
            val insert = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: return false //为空的话 直接失败返回了

            //这个打开了输出流  直接保存图片就好了
            context.contentResolver.openOutputStream(insert).use {
                it ?: return false
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
            return true
        } else {
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "title", "desc")
            return true
        }
    }

}