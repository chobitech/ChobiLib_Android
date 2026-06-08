package com.chobitech.lib.android.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.chobitech.lib.android.FireSwitch

@Composable
fun rememberTripleFireSwitch(
    onFired1: () -> Unit,
    onFired2: () -> Unit,
    onFired3: () -> Unit
): List<FireSwitch> {
    val currentOnFired1 by rememberUpdatedState(onFired1)
    val currentOnFired2 by rememberUpdatedState(onFired2)
    val currentOnFired3 by rememberUpdatedState(onFired3)

    val lists = remember(currentOnFired1, currentOnFired2, currentOnFired3) {
        listOf(
            FireSwitchAndEvent(FireSwitch(), { currentOnFired1() }),
            FireSwitchAndEvent(FireSwitch(), { currentOnFired2() }),
            FireSwitchAndEvent(FireSwitch(), { currentOnFired3() }),
        )
    }

    return rememberMultipleFireSwitch(
        fireSwitchAndEvents = lists
    )
}