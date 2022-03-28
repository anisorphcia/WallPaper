package com.asakao.wallpaper.ui.piclist

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.asakao.wallpaper.R
import com.asakao.wallpaper.logic.model.Vertical
import com.asakao.wallpaper.ui.preview.BigPictureActivity

class PicListAdapter(private val activity: Activity, private val picList: List<Vertical>) :
    RecyclerView.Adapter<PicListAdapter.ViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return picList.size
    }

}