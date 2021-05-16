package com.example.myapplication.givememyplace.model.http

import com.example.myapplication.core.net.AbstractHttpProtocol
import com.example.myapplication.givememyplace.model.dto.SeatInfo
import com.example.myapplication.givememyplace.model.dto.UsedTime

class UsedTimeProtocol : AbstractHttpProtocol<UsedTime.Response>() {

    override fun getUrl() = "http://10.0.1.37:7579/Mobius/infosec18/UsedTime/4-20210516150641511"


    override fun getRequestHeaderMap(): Map<String, String> {
        addRequestHeader("Host", "<calculated when request is sent>")
        addRequestHeader("User-Agent", "PostmanRuntime/7.26.8")
        addRequestHeader("Accept","*/*")
        addRequestHeader("Accept-Encoding","gzip, deflate, br")
        addRequestHeader("Connection","keep-alive")
        addRequestHeader("Accept","application/json")
        addRequestHeader("X-M2M-RI","12345")
        addRequestHeader("X-M2M-Origin","SOrigin")
        return super.getRequestHeaderMap()
    }
}