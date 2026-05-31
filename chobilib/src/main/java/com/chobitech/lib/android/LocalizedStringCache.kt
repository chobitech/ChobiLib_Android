package com.chobitech.lib.android

import android.content.Context
import androidx.annotation.StringRes


private val stringMap = hashMapOf<Int, String>()

fun getCachedResString(@StringRes resId: Int, vararg formatArgs: Any?, context: Context? = null): String? = stringMap[resId]
    ?: (context?.applicationContext ?: GlobalAppContext.appContext).getString(resId, *formatArgs).also {
        stringMap[resId] = it
    }

fun clearCachedResStrings() = stringMap.clear()
