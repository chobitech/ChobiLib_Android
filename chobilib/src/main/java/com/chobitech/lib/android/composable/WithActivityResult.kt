package com.chobitech.lib.android.composable

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.chobitech.lib.android.FireSwitch


@Composable
fun <I, O> WithActivityResult(
    argsGetter: () -> I,
    contractGenerator: () -> ActivityResultContract<I, O>,
    fireSwitch: FireSwitch? = null,
    onResult: (context: Context, result: O) -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) {
    val context = LocalContext.current

    val currentOnResult by rememberUpdatedState(onResult)
    val currentContractGenerator by rememberUpdatedState(contractGenerator)
    val currentArgsGetter by rememberUpdatedState(argsGetter)

    val launcher = rememberLauncherForActivityResult(
        contract = currentContractGenerator()
    ) { result ->
        currentOnResult(context, result)
    }

    val onFired = remember(launcher) {
        {
            launcher.launch(currentArgsGetter())
        }
    }

    WithFireSwitch(
        fireSwitch = fireSwitch,
        onFired = onFired,
        content = content
    )
}
