package com.example.myapplication.core.net

interface HttpTransactionCallback
{
    fun transactionBegin(protocol: HttpRequestable)

    fun transactionEnd(protocol: HttpRequestable)
}