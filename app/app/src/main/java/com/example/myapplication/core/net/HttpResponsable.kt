package com.example.myapplication.core.net

import com.example.myapplication.core.sys.Trace


interface HttpResponsable<in RES>
{
    fun onResponse(res: RES)

    fun onFailure(nError: Int, strMsg: String) {
        Trace.debug(">> onFailure() $strMsg[$nError]")
    }
}