package com.asakao.wallpaper.ui.piclist

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.asakao.wallpaper.R
import com.asakao.wallpaper.logic.model.Vertical
import com.asakao.wallpaper.ui.preview.BigPictureActivity

class PicListAdapter(private val activity: Activity, private val picList: List<Vertical>) :
    RecyclerView.Adapter<PicListAdapter.ViewHolder>() {

    private val selectedList = mutableListOf<Vertical>()
    private var isSelect = false

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pic: ImageView = view.findViewById(R.id.pic_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pic_item, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = picList[position]
        holder.pic.load(item.img)
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, BigPictureActivity::class.java)
            intent.putExtra("img", item.img)
            activity.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            if (!isSelect) {
                val color = Color.parseColor("#000000")
                holder.itemView.background = ColorDrawable(color)
                selectedList.add(item)
                isSelect = true
            } else {
                val color = Color.parseColor("#ffffff")
                holder.itemView.background = ColorDrawable(color)
                selectedList.remove(item)
                isSelect = false
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return picList.size
    }

    fun getSelectedList(): List<Vertical> {
        return selectedList
    }

}