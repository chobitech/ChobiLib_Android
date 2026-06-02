package com.chobitech.lib.android

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext


@Composable
fun <I, O> WithActivityResult(
    args: I,
    contractGenerator: () -> ActivityResultContract<I, O>,
    onResult: (context: Context, result: O) -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) {
    val context = LocalContext.current

    val currentOnResult by rememberUpdatedState(onResult)

    val currentArgs by rememberUpdatedState(args)

    val launcher = rememberLauncherForActivityResult(
        contract = contractGenerator()
    ) { result ->
        currentOnResult(context, result)
    }

    WithFireSwitch(
        onFired = { launcher.launch(currentArgs) },
        content = content
    )
}
