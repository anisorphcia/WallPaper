package com.asakao.wallpaper.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asakao.wallpaper.databinding.FragmentCategoryBinding

class CategoryFragment:Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(CategoryViewModel::class.java) }

    private lateinit var binding: FragmentCategoryBinding

    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = CategoryAdapter(this, viewModel.categoryList)
        binding.recyclerView.adapter = adapter
        binding.request.setOnClickListener {
            viewModel.getCategory((1..10).random())
        }
        viewModel.categoryLiveData.observe(viewLifecycleOwner) { result ->
            val category = result.getOrNull()
            if (category != null) {
                viewModel.categoryList.clear()
                viewModel.categoryList.addAll(category)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "cannot get the forecast successfully", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

}
