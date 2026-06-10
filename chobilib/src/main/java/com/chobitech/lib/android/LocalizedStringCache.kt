//package com.chobitech.lib.android
//
//import android.content.Context
//import androidx.annotation.StringRes
//
//
//private val stringMap = hashMapOf<Int, String>()
//
//fun Context.getCachedResString(@StringRes resId: Int, vararg formatArgs: Any?): String = stringMap[resId]
//    ?: getString(resId, *formatArgs).also {
//        stringMap[resId] = it
//    }
//
//fun clearCachedResStrings() = stringMap.clear()
