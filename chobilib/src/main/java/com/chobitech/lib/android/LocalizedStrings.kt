package com.chobitech.lib.android

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

object LocalizedStrings {

    private fun get(context: Context, @StringRes resId: Int): String {
        return context.getCachedResString(resId)
    }

    @Composable
    private fun get(@StringRes resId: Int): String {
        val context = LocalContext.current
        return context.getCachedResString(resId)
    }

    fun getYes(context: Context) = context.getCachedResString(R.string.yes)
    val yes: String
        @Composable get() = get(R.string.yes)



    fun getCancel(context: Context) = context.getCachedResString(R.string.cancel)
    val cancel: String
        @Composable get() = get(R.string.cancel)


    fun getOk(context: Context) = context.getCachedResString(R.string.ok)
    val ok: String
        @Composable get() = get(R.string.ok)


    fun getNo(context: Context) = context.getCachedResString(R.string.no)
    val no: String
        @Composable get() = get(R.string.no)


    fun getOpen(context: Context) = context.getCachedResString(R.string.open)
    val open: String
        @Composable get() = get(R.string.open)


    fun getClose(context: Context) = context.getCachedResString(R.string.close)
    val close: String
        @Composable get() = get(R.string.close)


    fun getSave(context: Context) = context.getCachedResString(R.string.save)
    val save: String
        @Composable get() = get(R.string.save)

}