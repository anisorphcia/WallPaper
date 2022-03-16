package com.asakao.wallpaper.ui.piclist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.asakao.wallpaper.logic.Repository
import com.asakao.wallpaper.logic.model.Vertical

class PicListViewModel: ViewModel() {

    private val getPicListLiveData = MutableLiveData<String>()

    val picList = ArrayList<Vertical>()

    val picListLiveData = Transformations.switchMap(getPicListLiveData){ query->
        Repository.getPicList(query)
    }

    fun getPicList(query: String){
        getPicListLiveData.value = query
    }

}