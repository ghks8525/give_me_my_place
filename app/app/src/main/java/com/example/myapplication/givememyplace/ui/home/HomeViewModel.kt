package com.example.myapplication.givememyplace.ui.home

import androidx.lifecycle.ViewModel
import com.example.myapplication.core.net.HttpResponsable
import com.example.myapplication.core.net.ProtocolFactory
import com.example.myapplication.core.sys.Trace
import com.example.myapplication.givememyplace.model.dto.SeatInfo
import com.example.myapplication.givememyplace.model.http.SeatInfoProtocol

class HomeViewModel : ViewModel() {

    fun requestData(){
        val protocol: SeatInfoProtocol = ProtocolFactory.create(SeatInfoProtocol::class.java)

        protocol.setHttpResponsable(object : HttpResponsable<SeatInfo.Response> {
            override fun onResponse(response: SeatInfo.Response) {
                Trace.debug(">> requestData() onResponse() : $response")

            }

            override fun onFailure(nError: Int, strMsg: String) {
                super.onFailure(nError, strMsg)
                Trace.debug(">> requestData() onFailure() : $strMsg")
            }
        })

    }
}