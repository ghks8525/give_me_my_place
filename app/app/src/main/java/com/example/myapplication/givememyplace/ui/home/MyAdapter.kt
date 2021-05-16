package com.example.myapplication.givememyplace.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.core.sys.Trace
import com.example.myapplication.givememyplace.model.dto.UsedTime
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

class MyAdapter(private var contents:MutableList<UsedTime.UsedTimeInfor>): BaseAdapter() {
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.view_my, parent, false)

        val content = contents[position]

        val etTime = toDateLong(content.endTime)
        val stTime = toDateLong(content.startTime)
        val totalTime = stTime - etTime
        Trace.debug("totaltime = $totalTime, ettime = $etTime, sttime = $stTime")


        convertView?.findViewById<TextView>(R.id.vm_tv_num)?.text = "좌석번호 : " + content.placeNum
        convertView?.findViewById<TextView>(R.id.vm_tv_time)?.text = content.startTime + " ~ " + content.endTime
        convertView?.findViewById<TextView>(R.id.vm_tv_total)?.text = "이용 시간 : " + content.totalTime


        return convertView
    }

    override fun getItem(position: Int): UsedTime.UsedTimeInfor = contents[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = contents.size

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