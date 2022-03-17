package com.asakao.wallpaper.ui.piclist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.asakao.wallpaper.databinding.ActivityPiclistBinding

class PicListActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(PicListViewModel::class.java) }

    lateinit var binding: ActivityPiclistBinding

    private lateinit var adapter: PicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPiclistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.picRecyclerView.layoutManager = layoutManager
        adapter = PicListAdapter(this, viewModel.picList)
        binding.picRecyclerView.adapter = adapter

        val picId = getIntent().getStringExtra("picId")

        if (picId != null) {
            viewModel.getPicList(picId)
        }

        viewModel.picListLiveData.observe(this){ result->
            val pics = result.getOrNull()
            if(pics != null){
                viewModel.picList.clear()
                viewModel.picList.addAll(pics)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this, "can not get any pics", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }

    }

}