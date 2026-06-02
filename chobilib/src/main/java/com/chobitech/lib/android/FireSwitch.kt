package com.chobitech.lib.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FireSwitch(
    private val scope: CoroutineScope
) {
    private val _fired = MutableSharedFlow<Unit>()
    val fired = _fired.asSharedFlow()

    fun fire() {
        scope.launch {
            _fired.emit(Unit)
        }
    }
}


@Composable
fun WithFireSwitch(
    onFired: () -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) {
    val scope = rememberCoroutineScope()

    val fireSwitch = remember(scope) {
        FireSwitch(scope)
    }

    val firedState by rememberUpdatedState(fireSwitch.fired)

    LaunchedEffect(Unit) {
        firedState.collect {
            onFired()
        }
    }

    content(fireSwitch)
}
