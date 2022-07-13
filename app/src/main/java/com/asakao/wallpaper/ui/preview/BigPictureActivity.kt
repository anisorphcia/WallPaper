package com.asakao.wallpaper.ui.preview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asakao.wallpaper.databinding.ActivityBigPictureBinding
import com.asakao.wallpaper.utils.MediaStoreUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class BigPictureActivity : AppCompatActivity() {

    lateinit var binding: ActivityBigPictureBinding

//    val viewModel by lazy { ViewModelProvider(this).get(PicList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBigPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img = getIntent().getStringExtra("img");

//        binding.ivBigPic.load(img)
        Glide.with(this).load(img).into(binding.ivBigPic);

        binding.ivDownload.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(img)
                .into(object  : CustomTarget<Bitmap>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        MediaStoreUtils.saveImages(this@BigPictureActivity, resource);
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }

}