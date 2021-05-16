package com.example.myapplication.givememyplace.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.core.sys.Trace
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.givememyplace.model.dto.CommonData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeBinding

    val mqttClient = MqttAndroidClient(this,"tcp://10.0.1.37:1883","a")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        mBinding.listener = this

        CommonData.setPlace()
        CommonData.setSeat()

        connect()

        Trace.debug("aaaaaaaaaaaaa = ${CommonData.mSeatInfo}")

        mBinding.compList.adapter = HomeAdapter(CommonData.mPlace, applicationContext)

        init()
    }

    fun init() {
        mBinding.compList.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent()
            if (CommonData.authFlag) {
                intent = Intent(this, AdminPlaceDialog::class.java)
                intent.putExtra("seat",position)
                startActivity(intent)
            } else {
                intent = Intent(this, UserPlaceDialog::class.java)
                intent.putExtra("seat", position)
                startActivityForResult(intent,10)
            }
        }

        if(CommonData.regist){
        }


//        CoroutineScope(Dispatchers.Main).launch {
//            var time = 3180
//            while (true) {
//                mBinding.time.text = "예상 사용 가능 시각 : " + toIntTime(time)
//                time -= 1
//                delay(1000L)
//            }
//        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 10){
            CommonData.seat = data!!.getIntExtra("seat",0)
            mBinding.compList[CommonData.seat].findViewById<ConstraintLayout>(R.id.comp_cl_place).background =
                applicationContext.getDrawable(R.drawable.bg_rect_full_gray_r10)
            mBinding.ahClNotUsedNotPlaceInfor.visibility = View.GONE
            mBinding.ahClUsedInfor.visibility = View.VISIBLE

            mBinding.ahTvUsedNum.text = String.format("사용 좌석 : %d번", CommonData.mPlace[CommonData.seat].seatNum)

            CoroutineScope(Dispatchers.Main).launch {
                var time = 0
                while (true){
                    mBinding.ahTvUsedTime.text = "사용 시간 : " + toIntTime(time)
                    time += 1
                    delay(1000L)
                }
            }
        }else{
            return
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.ah_btn_setting -> {
                startActivity(Intent(this, MyActivity::class.java))
            }
        }
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

    fun startTimer() {
        mBinding.compList[CommonData.seat].findViewById<ConstraintLayout>(R.id.comp_cl_place).background =
            applicationContext.getDrawable(R.drawable.bg_rect_full_red_r10)
        if (CommonData.seat != 0) {
            mBinding.compList[CommonData.seat].findViewById<ConstraintLayout>(R.id.comp_cl_place).background =
                applicationContext.getDrawable(R.drawable.bg_rect_full_red_r10)
            CoroutineScope(Dispatchers.Main).launch {
                var time = 0
                while (CommonData.seat != 0) {
                    mBinding.ahTvEmptyTime.text = "비운 시간 : " + toIntTime(time)
                    time += 1
                    delay(1000L)
                }
            }
        }else{
            CommonData.seat = 0
            mBinding.compList[CommonData.seat].findViewById<ConstraintLayout>(R.id.comp_cl_place).background =
                applicationContext.getDrawable(R.drawable.bg_rect_full_gray_r10)
        }
    }
    private var uniqueID:String? = null
    private val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

    init{
        Trace.debug("++ MqttAnroidClient init")
        mqttClient.setCallback(object: MqttCallbackExtended {

            override fun connectionLost(cause: Throwable?) {
                Trace.debug("++ MqttConnect Lost = $cause")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Trace.debug("++ MqttConnect topic = $topic, message = $message")
                startTimer()

            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Trace.debug("++ MqttConnect token = $token")
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Trace.debug("++ connectComplete")
            }
        })
    }

    fun connect() {

        val option = MqttConnectOptions()
        option.isAutomaticReconnect = true
        option.isCleanSession = false
        Trace.debug("++ MqttAnroidClient Connect")
        try{
            mqttClient.connect(option, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Trace.debug("MqttConnect onSuccess = $asyncActionToken")
//                    val disconnectedBufferOptions = DisconnectedBufferOptions()
//                    disconnectedBufferOptions.setBufferEnabled(true)
//                    disconnectedBufferOptions.setBufferSize(100)
//                    disconnectedBufferOptions.setPersistBuffer(false)
//                    disconnectedBufferOptions.setDeleteOldestMessages(false)
//                    mqttClient.setBufferOpts(disconnectedBufferOptions)
                    subscribe("/emptyplace")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Trace.debug("MqttConnect onFailure = $exception")
                }

            })
        }catch (e :  IllegalArgumentException){
            e.printStackTrace()
            Trace.debug("Exception = $e")
        }
    }

    fun disconnect(){
        try{
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    TODO("Not yet implemented")
                }

            })
        }catch (e: MqttException){
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 0, retained: Boolean = true){
        try{
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained

            mqttClient.publish(topic, message, null, object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Trace.debug("Publish Success topic = $topic, message = $message")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Trace.debug("Publish Failed exception = $exception")
                }

            })
        }catch (e: MqttException){
            e.printStackTrace()
        }
    }


    fun subscribe(topic: String, qos: Int = 0){
        Trace.debug("++ MqttAnroidClient Subscribe()")
        try{
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    System.out.println("++ subscribe $topic success")

                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Trace.debug("++ subscribe failed = $exception")
                }

            })
        }catch (e: MqttException){
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic: String){
        try{
            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    TODO("Not yet implemented")
                }

            })
        }catch (e: MqttException){
            e.printStackTrace()
        }

    }

    @Synchronized fun id(context: Context):String {
        if (uniqueID == null)
        {
            val sharedPrefs = context.getSharedPreferences(
                PREF_UNIQUE_ID, Context.MODE_PRIVATE)
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null)
            if (uniqueID == null)
            {
                uniqueID = UUID.randomUUID().toString()
                val editor = sharedPrefs.edit()
                editor.putString(PREF_UNIQUE_ID, uniqueID)
                editor.commit()
            }
        }
        return uniqueID!!
    }
}