package com.example.myapplication.givememyplace.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.core.sys.Trace
import com.example.myapplication.databinding.DialogPlaceAdminBinding
import com.example.myapplication.givememyplace.model.dto.Account
import com.example.myapplication.givememyplace.model.dto.CommonData
import com.example.myapplication.givememyplace.model.dto.SeatInfo
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

class AdminPlaceDialog :AppCompatActivity(){
    lateinit var mBinding:DialogPlaceAdminBinding
    var seat = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.dialog_place_admin)
        mBinding.listener = this

        init()

    }

    fun init(){
        seat = intent.getIntExtra("seat",0)
        var seatNum = CommonData.mPlace[seat].seatNum
        var seatInfo : SeatInfo.Seat? = null
        CommonData.setAccount()
        CommonData.mSeatInfo.forEach {
            if(it?.seatIdx == seatNum)
                seatInfo = it
        }

        if(seatInfo == null)
            finish()

        Trace.debug("aaaaaaaaaaaaa = ${CommonData.mAccount}")
        var account:Account.Accountinfor? = null
        CommonData.mAccount.forEach {
            if(it.id == seatInfo?.id) {
                Trace.debug("aaaaaaaaaaaaa = $it")
                account = it
            }
        }

        Trace.debug("aaaaaaaaaaaaa = $account")
        mBinding.dpTvWarn.text = String.format("경고 횟수 : %d번",account?.warningCount)
        mBinding.dpTvPlaceInfor.text = seatNum.toString() + "번"
        mBinding.dpTvId.text = "아이디 : " + seatInfo?.id
        Trace.debug("time = ${seatInfo?.startedTime}")
        if(seatInfo?.startedTime == null)
            finish()
        CoroutineScope(Dispatchers.Main).launch {
            val currentTime = System.currentTimeMillis()
            val time = seatInfo?.startedTime?.let { toDateLong(it) }
            var getTime = (currentTime - time!!).toInt()/20000
            var a = 0
            while (!this@AdminPlaceDialog.isFinishing){
                mBinding.dpTvUsedTime.text = "사용 시간 : " + toIntTime((getTime.toInt() + a))
                if(seatInfo?.warningLevel == true)
                    mBinding.dpTvEmptyTime.text = "비운 시간 : " + toIntTime(2751+a)
                a+=1
                delay(1000L)
            }
        }

    }


    fun onClick(v: View){
        when(v.id){
            R.id.dp_iv_exit -> {
                finish()
            }
        }
    }

    fun toDateLong(strExp: String): Long {
        val df: DateFormat = SimpleDateFormat("yyyy-mm-dd HH:MM:ss")
        val date = try {
            df.parse(strExp)
        } catch (e: ParseException) {
            e.printStackTrace()
            return 0L
        }

        Trace.debug("time = ${date.time}")
        return date.time
    }

    fun toIntTime(int:Int):String{
        var str = ""
        var time = int
        str += String.format("%02d:",time / 3600)
        time %= 3600
        str += String.format("%02d:", time /60)
        time %= 60
        str += String.format("%02d", time)
        return str
    }
}