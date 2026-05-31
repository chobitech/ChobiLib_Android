package com.chobitech.lib.android

import android.content.Context

object GlobalAppContext {

    lateinit var appContext: Context
        private set

    fun initialise(context: Context) {
        synchronized(this) {
            if (!::appContext.isInitialized) {
                appContext = context.applicationContext
            }
        }
    }

}