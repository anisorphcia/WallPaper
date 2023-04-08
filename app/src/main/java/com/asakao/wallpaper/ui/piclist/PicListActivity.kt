package com.asakao.wallpaper.ui.piclist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.asakao.wallpaper.R
import com.asakao.wallpaper.WallPaperApplication
import com.asakao.wallpaper.databinding.ActivityPiclistBinding
import com.asakao.wallpaper.logic.dao.VerticalDao
import com.asakao.wallpaper.logic.db.AppDatabase
import com.asakao.wallpaper.logic.model.Vertical
import com.asakao.wallpaper.ui.collection.CollectionActivity
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import showToast
import kotlin.concurrent.thread

class PicListActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(PicListViewModel::class.java) }

    lateinit var binding: ActivityPiclistBinding

    private lateinit var adapter: PicListAdapter

    private val selectedList = mutableListOf<Vertical>()

    var skip = 1;
    var limit = 10;
    var isLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPiclistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val verticalDao = AppDatabase.getDatabase(this).verticalDao()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.picRecyclerView.layoutManager = layoutManager
        adapter = PicListAdapter(this, viewModel.picList)
        binding.picRecyclerView.adapter = adapter

        binding.btnCollect.setOnClickListener {
            selectedList.clear()
            selectedList.addAll(adapter.getSelectedList())
            thread {
                for (item in selectedList) {
                    if (verticalDao.getVerticalById(item.id) == null) {
                        verticalDao.insertVertical(item)
                    }
                }
            }

            Log.d("TAG", "selectedList: " + selectedList.size)
            selectedList.size.toString().showToast(applicationContext)
        }

        binding.btnMine.setOnClickListener {
            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }


        val picId = getIntent().getStringExtra("picId")

        if (picId != null) {
            viewModel.getPicList(picId, skip, limit);
        }

        viewModel.picListLiveData.observe(this) { result ->
            val pics = result.getOrNull()
            if (pics != null) {
                if (!isLoadMore) {
                    viewModel.picList.clear()
                }
                viewModel.picList.addAll(pics)
                adapter.notifyDataSetChanged()
            } else {
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

    fun refresh() {
        val picId = getIntent().getStringExtra("picId")
        skip = 1;

        if (picId != null) {
            viewModel.getPicList(picId, skip, limit);
            binding.refresh.finishRefresh()
            isLoadMore = false
        }
    }

    fun loadMore() {
        val picId = getIntent().getStringExtra("picId")
        skip += 10;

        if (picId != null) {
            viewModel.getPicList(picId, skip, limit);
            binding.refresh.finishLoadMore()
            isLoadMore = true
        }
    }

}
