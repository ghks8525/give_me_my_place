package com.example.myapplication.givememyplace.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.core.sys.Trace
import com.example.myapplication.databinding.DialogPlaceUserBinding
import com.example.myapplication.givememyplace.model.dto.CommonData
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

class UserPlaceDialog: AppCompatActivity() {
    private lateinit var mBinding: DialogPlaceUserBinding
    var seat = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.dialog_place_user)
        mBinding.listener = this

        seat = intent.getIntExtra("seat",0)

        mBinding.dpuTvRegist.text = CommonData.mPlace[seat].seatNum.toString() + mBinding.dpuTvRegist.text

    }

    fun onClick(v: View){
        when(v.id){
            R.id.dpu_btn_regist -> {
                intent = Intent()
                intent.putExtra("seat",seat)
                setResult(10,intent)
                finish()
            }
            R.id.dpu_btn_cancel -> {
                setResult(55,intent)
                finish()
            }
        }
    }


}