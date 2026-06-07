package com.chobitech.lib.android.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import com.chobitech.lib.android.FireSwitch

@Composable
fun WithFireSwitch(
    fireSwitch: FireSwitch? = null,
    onFired: () -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) {
    val scope = rememberCoroutineScope()

    val innerFireSwitch = fireSwitch ?: remember(scope) {
        FireSwitch(scope)
    }

    val currentOnFired by rememberUpdatedState(onFired)

    LaunchedEffect(innerFireSwitch) {
        innerFireSwitch.fired.collect {
            currentOnFired()
        }
    }

    content(innerFireSwitch)
}
