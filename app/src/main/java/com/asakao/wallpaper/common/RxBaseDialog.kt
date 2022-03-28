package com.asakao.wallpaper.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asakao.wallpaper.R
import com.trello.rxlifecycle2.components.support.RxDialogFragment

abstract class RxBaseDialog : RxDialogFragment() {

    private var parentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Dialog_Alert)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater!!.inflate(getLayoutResId(), container, false)
        initView()
        return parentView
    }

    protected abstract fun getLayoutResId(): Int

    protected abstract fun initView()

    protected fun findViewById(id: Int): View {
        return parentView!!.findViewById(id)
    }
}