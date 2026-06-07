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


