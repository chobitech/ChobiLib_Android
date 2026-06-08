package com.chobitech.lib.android.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.chobitech.lib.android.FireSwitch


@Composable
fun WithFireSwitch(
    fireSwitchAndEvent: FireSwitchAndEvent,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) {
    val currentFireSwitch by rememberUpdatedState(fireSwitchAndEvent.fireSwitch)
    val currentOnFire by rememberUpdatedState(fireSwitchAndEvent.onFired)

    LaunchedEffect(currentFireSwitch) {
        currentFireSwitch.fired.collect {
            currentOnFire()
        }
    }

    content(currentFireSwitch)
}

@Composable
fun WithFireSwitch(
    fireSwitch: FireSwitch? = null,
    onFired: () -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) {
    val currentFireSwitch = remember(fireSwitch) {
        fireSwitch ?: FireSwitch()
    }

    val fwAndEvent = remember(currentFireSwitch, onFired) {
        FireSwitchAndEvent(currentFireSwitch, onFired)
    }

    WithFireSwitch(
        fireSwitchAndEvent = fwAndEvent,
        content = content
    )
}
