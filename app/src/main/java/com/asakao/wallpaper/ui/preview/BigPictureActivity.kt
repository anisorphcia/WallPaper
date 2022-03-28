package com.asakao.wallpaper.ui.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.asakao.wallpaper.databinding.ActivityBigPictureBinding
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

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
            if (img != null) {
                downloadImage(img)
            }
        }
    }

    fun downloadImage(url: String){
        var conn: HttpURLConnection? = null
        try {
            conn = URL(url).openConnection() as HttpURLConnection
            conn.connect()
            conn.inputStream.use { input->
                BufferedOutputStream(FileOutputStream("./download.png")).use { output->
                    input.copyTo(output)
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            conn?.disconnect()
        }
    }

}