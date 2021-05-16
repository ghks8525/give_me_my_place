package com.example.myapplication.core.sys

class Config {
    companion object{
        const val SUPPORT_DEBUG = false
        const val SUPPORT_DEV_MODE = !SUPPORT_DEBUG
    }
}