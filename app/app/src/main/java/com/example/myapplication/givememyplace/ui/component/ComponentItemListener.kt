package com.example.myapplication.givememyplace.ui.component

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

interface ComponentItemListener {
    fun onItemBind(parentPos: Int, itemBinding: ViewDataBinding, itemPos: Int)

    fun onClick(v: View, pos: Int)

    fun onItemClick(parent: View, parentPos: Int, item: View, pos: Int)
}