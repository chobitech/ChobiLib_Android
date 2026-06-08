package com.chobitech.lib.android.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.chobitech.lib.android.FireSwitch

@Composable
fun WithTripleFireSwitch(
    fireSwitchAndEvent1: FireSwitchAndEvent,
    fireSwitchAndEvent2: FireSwitchAndEvent,
    fireSwitchAndEvent3: FireSwitchAndEvent,
    content: @Composable (fireSwitch1: FireSwitch, fireSwitch2: FireSwitch, fireSwitch3: FireSwitch) -> Unit
) {
    val fseList = remember(
        fireSwitchAndEvent1,
        fireSwitchAndEvent2,
        fireSwitchAndEvent3
    ) {
        listOf(fireSwitchAndEvent1, fireSwitchAndEvent2, fireSwitchAndEvent3)
    }

    WithMultipleFireSwitch(
        fireSwitchEventList = fseList
    ) { swList ->
        content(swList[0], swList[1], swList[2])
    }
}


@Composable
fun WithTripleFireSwitch(
    fireSwitch1: FireSwitch? = null,
    onFired1: () -> Unit,
    fireSwitch2: FireSwitch? = null,
    onFired2: () -> Unit,
    fireSwitch3: FireSwitch? = null,
    onFired3: () -> Unit,
    content: @Composable (fireSwitch1: FireSwitch, fireSwitch2: FireSwitch, fireSwitch3: FireSwitch) -> Unit
) {
    val fs1 = remember(fireSwitch1) { fireSwitch1 ?: FireSwitch() }
    val fs2 = remember(fireSwitch2) { fireSwitch2 ?: FireSwitch() }
    val fs3 = remember(fireSwitch3) { fireSwitch3 ?: FireSwitch() }

    WithTripleFireSwitch(
        fireSwitchAndEvent1 = remember(fs1, onFired1) {FireSwitchAndEvent(fs1, onFired1) },
        fireSwitchAndEvent2 = remember(fs2, onFired2) { FireSwitchAndEvent(fs2, onFired2) },
        fireSwitchAndEvent3 = remember(fs3, onFired3) { FireSwitchAndEvent(fs3, onFired3) },
        content = content
    )
}
