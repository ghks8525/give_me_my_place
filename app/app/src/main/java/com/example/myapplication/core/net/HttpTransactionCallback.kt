package com.example.myapplication.core.net

interface HttpTransactionCallback
{
    fun transactionBegin()

    fun transactionEnd()
}