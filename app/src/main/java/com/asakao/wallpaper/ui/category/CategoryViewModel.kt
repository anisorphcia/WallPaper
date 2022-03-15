package com.asakao.wallpaper.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.asakao.wallpaper.logic.Repository
import com.asakao.wallpaper.logic.model.Category

class CategoryViewModel : ViewModel() {

    private val getCategoryLiveData = MutableLiveData<Int>()

    val categoryList = ArrayList<Category>()

    val categoryLiveData  = Transformations.switchMap(getCategoryLiveData){ _ ->
        Repository.getCategory()
    }

    fun getCategory(query: Int){
        getCategoryLiveData.value = query
    }

}