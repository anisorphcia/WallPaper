package com.asakao.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.asakao.wallpaper.databinding.ActivityMainBinding
import com.asakao.wallpaper.ui.category.CategoryViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val viewModel by lazy { ViewModelProvider(this).get(CategoryViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getInfo.setOnClickListener {
            val num = (1..10).random()
            viewModel.getCategory(num)
        }
        viewModel.categoryLiveData.observe(this, Observer { result ->
            val category = result.getOrNull()
            if (category != null) {
                Log.d("TAG", "onCreate: " + category)
            }else{
                Toast.makeText(this, "cannot get the forecast successfully", Toast.LENGTH_SHORT)
                    .show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    // https://www.jianshu.com/p/fb1d1ad58a0b
}