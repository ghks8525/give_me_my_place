package com.example.myapplication.givememyplace.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegistBinding

class RegistActivity: AppCompatActivity() {
    private lateinit var mBinding: ActivityRegistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_regist)
        mBinding.listener = this

    }
    fun onClick(v: View){
        if(v.id == R.id.confirm)
            finish()
    }
}