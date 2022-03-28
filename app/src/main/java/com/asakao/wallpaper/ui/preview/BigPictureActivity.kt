package com.asakao.wallpaper.ui.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.asakao.wallpaper.databinding.ActivityBigPictureBinding

class BigPictureActivity: AppCompatActivity() {

    lateinit var binding: ActivityBigPictureBinding

//    val viewModel by lazy { ViewModelProvider(this).get(PicList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBigPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val img = getIntent().getStringExtra("img");

        binding.ivBigPic.load(img)

        binding.ivDownload.setOnClickListener{
//            if (img != null) {
//                downloadImage(img)
//            }
        }
    }

}