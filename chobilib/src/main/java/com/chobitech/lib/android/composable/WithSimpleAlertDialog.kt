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
fun WithSimpleAlertDialog(
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

    val currentTitle by rememberUpdatedState(title)
    val currentText by rememberUpdatedState(text)

    val innerCloseSwitch = remember(closeSwitch) {
        closeSwitch ?: FireSwitch()
    }


    val currentConfirmButton: @Composable () -> Unit = remember(confirmButton) {
        {
            confirmButton(innerCloseSwitch)
        }
    }

    val currentDismissButton: @Composable (() -> Unit)? = remember(dismissButton) {
        {
            dismissButton?.invoke(innerCloseSwitch)
        }
    }


    WithFireSwitch(
        fireSwitch = openSwitch,
        onFired = {
            showDialog = true
        }
    ) { oSwitch ->

        WithFireSwitch(
            fireSwitch = innerCloseSwitch,
            onFired = {
                showDialog = false
            }
        ) { cSwitch ->

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

            content(oSwitch, cSwitch)
        }
    }

}