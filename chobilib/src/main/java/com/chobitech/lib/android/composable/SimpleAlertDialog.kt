package com.chobitech.lib.android.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.chobitech.lib.android.FireSwitch


@Composable
fun SimpleAlertDialog(
    openSwitch: FireSwitch? = null,
    closeSwitch: FireSwitch? = null,
    allowOutsideTapClose: Boolean = true,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable (closeSwitch: FireSwitch) -> Unit,
    dismissButton: @Composable ((closeSwitch: FireSwitch) -> Unit)? = null,
    content: @Composable (openSwitch: FireSwitch, closeSwitch: FireSwitch) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val currentOpen = remember {
        {
            showDialog = true
        }
    }

    val currentClose = remember {
        {
            showDialog = false
        }
    }

    val currentTitle by rememberUpdatedState(title)
    val currentText by rememberUpdatedState(text)


    WithDoubleFireSwitch(
        fireSwitch1 = openSwitch,
        fireSwitch2 = closeSwitch,
        onFired1 = currentOpen,
        onFired2 = currentClose,
    ) { openSw, closeSw ->

        val currentConfirmButton = remember(confirmButton) {
            @Composable {
                confirmButton(closeSw)
            }
        }

        val currentDismissButton = remember(dismissButton) {
            dismissButton?.let {
                @Composable {
                    it(closeSw)
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    if (allowOutsideTapClose) {
                        showDialog = false
                    }
                },
                title = currentTitle,
                text = currentText,
                confirmButton = currentConfirmButton,
                dismissButton = currentDismissButton
            )
        }

        content(openSw, closeSw)
    }
}
