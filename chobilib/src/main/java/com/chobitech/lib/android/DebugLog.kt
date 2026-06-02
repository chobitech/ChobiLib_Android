package com.chobitech.lib.android

import android.util.Log


object DebugLog {

    var isShow = true
    var tag: String = "DEBUG"

    private val clsName = DebugLog::class.java.name
    private val iName = ::i.name
    private val wName = ::w.name
    private val eName = ::e.name

    private fun getMethodName(st: StackTraceElement?): String =
        st?.let {
            val cName = st.className
                .substringAfterLast(".")
                .substringBefore("$")
                .removeSuffix("Kt")
            " (${cName}.${st.methodName}(), ${st.fileName}:${st.lineNumber})"
        } ?: ""

    @JvmOverloads
    fun i(o: Any?, tempTag: String? = null) {
        if (!isShow) {
            return
        }

        val st = getCallStackTrace(iName)
        Log.i(tempTag ?: tag, "$o${getMethodName(st)}")
    }

    @JvmOverloads
    fun w(o: Any?, tempTag: String? = null) {
        if (!isShow) {
            return
        }

        val st = getCallStackTrace(wName)
        Log.w(tempTag ?: tag, "$o${getMethodName(st)}")
    }

    @JvmOverloads
    fun e(o: Any?, tempTag: String? = null) {
        if (!isShow) {
            return
        }

        val st = getCallStackTrace(eName)
        Log.e(tempTag ?: tag, "$o${getMethodName(st)}")
    }

    @JvmOverloads
    fun e(e: Throwable, tempTag: String? = null) {
        if (!isShow) {
            return
        }

        val st = getCallStackTrace(eName)
        Log.e(tempTag ?: tag, "${e.localizedMessage}${getMethodName(st)}", e)
    }

    private fun getCallStackTrace(mName: String): StackTraceElement? {
        val stackTrace = Thread.currentThread().stackTrace

        var isCallingMethod = false

        for (st in stackTrace) {
            if (!isCallingMethod && st.className == clsName && st.methodName == mName) {
                isCallingMethod = true
            } else if (isCallingMethod) {
                return st
            }
        }

        return null
    }
}
