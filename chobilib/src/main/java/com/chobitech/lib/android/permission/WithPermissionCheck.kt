package com.chobitech.lib.android.permission

import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.chobitech.lib.android.FireSwitch
import com.chobitech.lib.android.composable.WithActivityResult
import com.chobitech.lib.android.findActivity



private val withPermissionCheckContractGenerator = {
    ActivityResultContracts.RequestMultiplePermissions()
}

@Composable
fun WithPermissionCheck(
    permissions: Array<String>,
    fireSwitch: FireSwitch? = null,
    onResult: (Map<String, PermissionCheckResult>) -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) {
    val currentPermissionsGetter = remember(permissions) {
        {
            permissions
        }
    }

    val currentArgOnResult by rememberUpdatedState(onResult)

    val currentOnResult: (Context, Map<String, Boolean>) -> Unit =
        remember(currentArgOnResult) {
            { ctx, res ->
                val activity = ctx.findActivity() ?: return@remember

                val rMap = res.mapValues { (p, isGranted) ->
                    when (isGranted) {
                        true -> PermissionCheckResult.GRANTED
                        false -> when (activity.shouldShowRequestPermissionRationale(p)) {
                            true -> PermissionCheckResult.DENIED
                            false -> PermissionCheckResult.PERMANENTLY_DENIED
                        }
                    }
                }

                currentArgOnResult(rMap)
            }
        }

    WithActivityResult(
        argsGetter = currentPermissionsGetter,
        fireSwitch = fireSwitch,
        contractGenerator = withPermissionCheckContractGenerator,
        onResult = currentOnResult,
        content = content
    )
}