package com.example.myapplication.core.sys

import android.os.Environment
import com.example.myapplication.BuildConfig
import com.example.myapplication.core.app.ApplicationProxy

class Const
{
    class NETWORK {
        class DEV_SERVER {
            companion object {
                const val DOMAIN = "http://10.0.1.37:7579"
                const val PATH = "Mobius"
            }
        }

        class OPR_SERVER {
            companion object {
                const val DOMAIN = "http://10.0.1.37:7579"
                const val PATH = "Mobius"
            }
        }
    }

    class STORAGE {
        companion object {
            var APP_ROOT = ApplicationProxy.getContext().filesDir.toString()

            var APP_MAIN = "$APP_ROOT/storage"

            var EXT_ROOT = ApplicationProxy.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()

            var APP_CACHE = "${ApplicationProxy.getContext().cacheDir}/${BuildConfig.APPLICATION_ID}"

            const val FILE_READ_BUFFER_SIZE = 1024 * 10

            const val CACHE_SIZE_MAX = 1024 * 1024 * 10
        }
    }
}