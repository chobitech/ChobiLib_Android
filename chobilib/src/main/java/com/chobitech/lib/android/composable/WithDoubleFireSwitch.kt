package com.chobitech.lib.android.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.chobitech.lib.android.FireSwitch

@Composable
fun WithDoubleFireSwitch(
    fireSwitchAndEvent1: FireSwitchAndEvent,
    fireSwitchAndEvent2: FireSwitchAndEvent,
    content: @Composable (fireSwitch1: FireSwitch, fireSwitch2: FireSwitch) -> Unit
) {
    val fseList = remember(fireSwitchAndEvent1, fireSwitchAndEvent2) {
        listOf(fireSwitchAndEvent1, fireSwitchAndEvent2)
    }

    WithMultipleFireSwitch(
        fireSwitchEventList = fseList
    ) { fireSwitchList ->
        content(fireSwitchList[0], fireSwitchList[1])
    }
}


@Composable
fun WithDoubleFireSwitch(
    fireSwitch1: FireSwitch? = null,
    onFired1: () -> Unit,
    fireSwitch2: FireSwitch? = null,
    onFired2: () -> Unit,
    content: @Composable (fireSwitch1: FireSwitch, fireSwitch2: FireSwitch) -> Unit
) {
    val fs1 = remember(fireSwitch1) { fireSwitch1 ?: FireSwitch() }
    val fs2 = remember(fireSwitch2) { fireSwitch2 ?: FireSwitch() }

    WithDoubleFireSwitch(
        fireSwitchAndEvent1 = remember(fs1, onFired1) { FireSwitchAndEvent(fs1, onFired1) },
        fireSwitchAndEvent2 = remember(fs2, onFired2) { FireSwitchAndEvent(fs2, onFired2) },
        content = content
    )
}
