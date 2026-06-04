package com.chobitech.lib.android

import android.content.Context
import androidx.annotation.StringRes


private val stringMap = hashMapOf<Int, String>()

fun getCachedResString(context: Context, @StringRes resId: Int, vararg formatArgs: Any?): String = stringMap[resId]
    ?: context.getString(resId, *formatArgs).also {
        stringMap[resId] = it
    }

fun clearCachedResStrings() = stringMap.clear()
