package com.example.myapplication.givememyplace.ui.home

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMyBinding
import com.example.myapplication.givememyplace.model.dto.CommonData

class MyActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMyBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my)
        mBinding.amIvProfile.clipToOutline = true
        mBinding.listener = this

        CommonData.setUsedTime()

        mBinding.compList.adapter = MyAdapter(CommonData.mUsedTime)
    }

    fun onClick(v: View){
        if(v.id == R.id.am_iv_exit)
            finish()
    }
}