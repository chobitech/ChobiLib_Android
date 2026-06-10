package com.chobitech.lib.android.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.chobitech.lib.android.FireSwitch


@Composable
fun WithMultipleFireSwitch(
    fireSwitchEventList: List<FireSwitchAndEvent>,
    content: @Composable (fireSwitchList: List<FireSwitch>) -> Unit
) {
    val swList = rememberMultipleFireSwitch(
        fireSwitchAndEvents = fireSwitchEventList
    )

    content(swList)
}

@Composable
fun WithMultipleFireSwitch(
    onFiredList: List<() -> Unit>,
    content: @Composable (fireSwitchList: List<FireSwitch>) -> Unit
) {
    val swList = rememberMultipleFireSwitch(
        onFiredList = onFiredList
    )

    content(swList)
}

