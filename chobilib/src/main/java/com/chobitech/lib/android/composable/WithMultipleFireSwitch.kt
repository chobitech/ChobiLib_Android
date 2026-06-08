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
    val switchList = remember(fireSwitchEventList) {
        fireSwitchEventList.map { it.fireSwitch }
    }

    for (fse in fireSwitchEventList) {
        key(fse.fireSwitch.uuid) {
            FireSwitchAndEventObserver(fse)
        }
    }

    content(switchList)
}
