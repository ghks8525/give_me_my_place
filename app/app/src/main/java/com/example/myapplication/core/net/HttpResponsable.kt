package com.example.myapplication.core.net

interface HttpResponsable<in RES>
{
    fun onResponse(res: RES)

    fun onFailure(nError: Int, strMsg: String)
}