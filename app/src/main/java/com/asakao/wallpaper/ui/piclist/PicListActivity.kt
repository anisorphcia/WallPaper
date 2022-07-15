package com.asakao.wallpaper.ui.piclist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.asakao.wallpaper.R
import com.asakao.wallpaper.databinding.ActivityPiclistBinding
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class PicListActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(PicListViewModel::class.java) }

    lateinit var binding: ActivityPiclistBinding

    private lateinit var adapter: PicListAdapter

    var skip = 1;
    var limit = 10;
    var isLoadMore = false

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
            viewModel.getPicList(picId, skip, limit);
        }

        viewModel.picListLiveData.observe(this){ result->
            val pics = result.getOrNull()
            if(pics != null){
                if(!isLoadMore){
                    viewModel.picList.clear()
                }
                viewModel.picList.addAll(pics)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this, "can not get any pics", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }

        binding.refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                refresh();
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                loadMore();
            }
        })
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(this)
            .reset()
            .statusBarColor(R.color.white)
            .navigationBarColor(R.color.white)
            .statusBarDarkFont(true, 0.2f)
            .init()
    }

    fun refresh(){
        val picId = getIntent().getStringExtra("picId")
        skip = 1;

        if (picId != null) {
            viewModel.getPicList(picId, skip, limit);
            binding.refresh.finishRefresh()
            isLoadMore = false
        }
    }

    fun loadMore(){
        val picId = getIntent().getStringExtra("picId")
        skip += 10;

        if (picId != null) {
            viewModel.getPicList(picId, skip, limit);
            binding.refresh.finishLoadMore()
            isLoadMore = true
        }
    }

}
