package com.example.myapplication.givememyplace.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.givememyplace.ui.home.HomeActivity

class LoginActivity: AppCompatActivity() {
    private lateinit var mBinding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.listener = this


    }

    fun onClick(v: View){
        var intent = Intent()
        when(v.id){
            R.id.al_btn_login -> {
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }

            R.id.al_tv_regist -> {
                intent = Intent(this, RegistActivity::class.java)
                startActivity(intent)
            }
        }
    }
}