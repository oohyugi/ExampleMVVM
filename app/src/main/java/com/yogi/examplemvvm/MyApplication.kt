package com.yogi.examplemvvm

import android.app.Application
import android.content.Context

/**
 * Created by oohyugi on 2019-09-01.
 * github: https://github.com/oohyugi
 */
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance: Application
        fun getContext(): Context = instance.applicationContext
    }
}