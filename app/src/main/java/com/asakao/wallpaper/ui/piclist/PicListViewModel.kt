package com.asakao.wallpaper.ui.piclist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.asakao.wallpaper.logic.Repository
import com.asakao.wallpaper.logic.model.BaseQuery
import com.asakao.wallpaper.logic.model.Vertical

class PicListViewModel: ViewModel() {

    private val getPicListLiveData = MutableLiveData<BaseQuery>()

    val picList = ArrayList<Vertical>()

    val picListLiveData = Transformations.switchMap(getPicListLiveData){ baseQuery->
        Repository.getPicList(baseQuery.id, baseQuery.skip, baseQuery.limit)
    }

    fun getPicList(query: String, skip: Int, limit: Int){
        getPicListLiveData.value = BaseQuery(query, skip, limit)
    }

}