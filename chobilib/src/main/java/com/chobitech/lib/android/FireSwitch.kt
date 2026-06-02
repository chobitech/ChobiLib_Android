package com.chobitech.lib.android

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