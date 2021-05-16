package com.example.myapplication.givememyplace.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.core.app.ApplicationProxy
import com.example.myapplication.core.sys.Trace
import com.example.myapplication.givememyplace.model.dto.CommonData
import com.example.myapplication.givememyplace.model.dto.Place
import java.lang.Exception

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeAdapter(private var contents: MutableList<Place>, val context: Context) : BaseAdapter() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "CutPasteId", "SimpleDateFormat", "UseCompatLoadingForDrawables")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView =
            LayoutInflater.from(parent?.context).inflate(R.layout.view_place, parent, false)

        val content = contents[position]
        if (content.flag) {
            convertView?.findViewById<ConstraintLayout>(R.id.comp_cl_place)?.visibility =
                View.VISIBLE
            Trace.debug(" position $position visibility = ${
                convertView?.findViewById<ConstraintLayout>(R.id.comp_cl_place)?.visibility
            }")
        } else
            convertView?.findViewById<ConstraintLayout>(R.id.comp_cl_place)?.visibility =
                View.INVISIBLE

        var idx = 0
        try {
            while (CommonData.mSeatInfo[idx] != null) {
                if (content.seatNum == CommonData.mSeatInfo[idx]?.seatIdx) {
                    if (CommonData.mSeatInfo[idx]?.warningLevel == true) {
                        convertView?.findViewById<ConstraintLayout>(R.id.comp_cl_place)?.background =
                            context.getDrawable(R.drawable.bg_rect_full_red_r10)
                        break
                    } else {
                        convertView?.findViewById<ConstraintLayout>(R.id.comp_cl_place)?.background =
                            context.getDrawable(R.drawable.bg_rect_full_gray_r10)
                        break
                    }
                }
                idx++
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        Trace.debug("aaaaaaaaaaaaaaaaa pos = $position")
        convertView?.findViewById<TextView>(R.id.vp_tv_place_num)?.text =
            String.format("%02d", content.seatNum)
        return convertView
    }

    override fun getItem(position: Int): Place = contents[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = contents.size
}