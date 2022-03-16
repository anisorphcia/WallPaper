package com.asakao.wallpaper.ui.category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.asakao.wallpaper.R
import com.asakao.wallpaper.logic.model.Category
import com.asakao.wallpaper.ui.piclist.PicListViewModel

class CategoryAdapter(private val fragment: Fragment, private val categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    val viewModel by lazy { ViewModelProvider(fragment).get(PicListViewModel::class.java) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.category_title)
        val bg: ImageView = view.findViewById(R.id.category_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categoryList[position]
        holder.title.text = item.name
        holder.bg.load(item.cover)
        holder.itemView.setOnClickListener {
            viewModel.getPicList(item.id)
        }
        viewModel.picListLiveData.observe(fragment){ result->
            val picList = result.getOrNull()
            if(picList != null){
                Log.d("TAG", "onBindViewHolder: "+picList)
            }else{
                Toast.makeText(fragment.context, "cannot get the picList info successfully", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}