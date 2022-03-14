package com.asakao.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asakao.wallpaper.databinding.ActivityMainBinding
import com.asakao.wallpaper.logic.Repository

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getInfo.setOnClickListener {
            Repository.getCategory()
        }
    }

    // https://www.jianshu.com/p/fb1d1ad58a0b
}