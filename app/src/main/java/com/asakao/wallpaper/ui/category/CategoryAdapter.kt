package com.asakao.wallpaper.ui.category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.asakao.wallpaper.R
import com.asakao.wallpaper.logic.model.Category

class CategoryAdapter(private val fragment: Fragment, private val categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.category_title)
        val bg: ImageView = view.findViewById(R.id.category_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            // TODO: 2022/3/15
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categoryList[position]
        holder.title.text = item.name
        holder.bg.load(item.cover)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}