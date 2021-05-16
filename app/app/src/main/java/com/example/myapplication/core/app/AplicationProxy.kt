package com.example.myapplication.core.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDexApplication
import com.example.myapplication.core.sys.Trace
import com.example.myapplication.givememyplace.SplashActivity
import com.example.myapplication.givememyplace.ui.home.HomeActivity


class ApplicationProxy : MultiDexApplication()
{
    private var mbInitLaunch = false;
    private lateinit var mActivity: Activity
    private lateinit var mLastActivity: Activity
    private var mAppCompatActivity: AppCompatActivity? = null
    private var mLifeCycle: LifeCycle = LifeCycle()
    private var mLifeCycleListener: LifeCycleListener? = null
    private var mnActivityAlive = 0
    private var mbBackground = false

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mInstance: ApplicationProxy

        @JvmStatic
        fun getInstance(): ApplicationProxy = mInstance

        @JvmStatic
        fun getContext(): Context = getInstance().applicationContext
    }

    fun getActivity(): AppCompatActivity? {
        if (mActivity is AppCompatActivity) {
            mAppCompatActivity = mActivity as AppCompatActivity
        }

        return mAppCompatActivity
    }

    fun getLastActivity() = mLastActivity

    override fun onCreate() {
        Trace.debug("++ Application onCreate()")
        super.onCreate()
        mInstance = this
        mbInitLaunch = true
        registerActivityLifecycleCallbacks(mLifeCycle)
        initialize()
    }

    override fun onTerminate() {
        Trace.debug("++ Application onTerminate()")
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(mLifeCycle)
    }

    private fun initialize() {
        Trace.debug("++ initialize()")
    }


    fun isApplicationBackground() = (mnActivityAlive == 0)

    fun addLifeCycleListener(l: LifeCycleListener) {
        mLifeCycleListener = l
    }

    fun removeLifeCycleListener(l: LifeCycleListener) {
        mLifeCycleListener = null
    }

    interface LifeCycleListener {
        fun onForeground(activity: Activity)
        fun onBackground(activity: Activity)
    }

    inner class LifeCycle : Application.ActivityLifecycleCallbacks
    {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Trace.debug("++ onActivityCreated() : ${activity.localClassName}")
            mActivity = activity

            if (activity.localClassName.contains(HomeActivity::class.simpleName.toString())) {
                Trace.debug(">> initial activity is ${activity.localClassName}");
                initialize()
            }
        }

        override fun onActivityStarted(activity: Activity) {
            mnActivityAlive++
            Trace.debug("++ onActivityStarted() : ${activity.localClassName} mnActivityAlive = $mnActivityAlive")

            if (mbBackground && mnActivityAlive == 1) {
                mbBackground = false
                mLifeCycleListener?.onForeground(activity)
            }

            if (mbInitLaunch) {
                mbInitLaunch = false

                if (activity.localClassName != SplashActivity::class.simpleName) {
                    Trace.debug(">> initial activity is not ${SplashActivity::class.simpleName}");
                    activity.finish()
                    val intent = Intent(getContext(), HomeActivity::class.java)
                    // fixme ?
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }

        override fun onActivityResumed(activity: Activity) {
            Trace.debug("++ onActivityResumed() : ${activity.localClassName}")
            mActivity = activity
        }

        override fun onActivityPaused(activity: Activity) {
            Trace.debug("++ onActivityPaused() : ${activity.localClassName}")
            mLastActivity = mActivity
        }

        override fun onActivityStopped(activity: Activity) {
            mnActivityAlive--
            Trace.debug("++ onActivityStopped() : ${activity.localClassName} mnActivityAlive = $mnActivityAlive")

            if (!mbBackground && mnActivityAlive == 0) {
                mbBackground = true
                mLifeCycleListener?.onBackground(activity)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            Trace.debug("++ onActivitySaveInstanceState() : ${activity.localClassName}")
        }

        override fun onActivityDestroyed(activity: Activity) {
            Trace.debug("++ onActivityDestroyed() : ${activity.localClassName}")

            if (activity.localClassName.contains(HomeActivity::class.simpleName.toString())) {
                Trace.debug(">> final activity is ${activity.localClassName}");
            }
        }
    }
}