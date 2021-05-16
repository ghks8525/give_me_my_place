package com.example.myapplication.givememyplace

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySplashBinding
import com.example.myapplication.givememyplace.model.dto.CommonData
import com.example.myapplication.givememyplace.ui.home.HomeActivity
import com.example.myapplication.givememyplace.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding =
            DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            if (CommonData.authFlag)
                startActivity(Intent(this,HomeActivity::class.java))
            else
                startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }, 3000)
    }
}