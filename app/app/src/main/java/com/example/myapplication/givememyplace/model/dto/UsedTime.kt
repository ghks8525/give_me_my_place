package com.example.myapplication.givememyplace.model.dto

class UsedTime {
    data class Response(val data: Data) : BaseResponse() {
        override fun toString(): String {
            return super.toString() + data.toString()
        }

        data class Data(val m2m: Cin)

        data class Cin(
            val pi: String,
            val ri: String,
            val ty: Int,
            val ct: String,
            val st: Int,
            val rn: String,
            val lt: String,
            val et: String,
            val cs: Int,
            val cr: String,
            val con: String,
        )
    }
    data class UsedTimeInfor(
    val placeNum: String,
    val startTime: String,
    val endTime: String,
    val totalTime: String
    )
}