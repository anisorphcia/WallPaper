package com.asakao.wallpaper.ui.collection

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.asakao.wallpaper.databinding.ActivityCollectionBinding
import com.asakao.wallpaper.logic.db.AppDatabase
import com.asakao.wallpaper.logic.model.Vertical
import com.asakao.wallpaper.ui.piclist.PicListAdapter
import kotlin.concurrent.thread

class CollectionActivity : AppCompatActivity() {

    lateinit var binding: ActivityCollectionBinding

    var collections = ArrayList<Vertical>()

    private lateinit var adapter: PicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCollectionList()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.collectionRecyclerView.layoutManager = layoutManager
        adapter = PicListAdapter(this, collections)
        binding.collectionRecyclerView.adapter = adapter
    }

    fun getCollectionList() {
        val verticalDao = AppDatabase.getDatabase(this).verticalDao()
        thread {
            collections.clear()
            for (item in verticalDao.getAllVertical()) {
                collections.add(item)
            }
        }
    }

}