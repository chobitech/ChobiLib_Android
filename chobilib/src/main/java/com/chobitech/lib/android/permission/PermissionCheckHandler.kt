package com.chobitech.lib.android.permission

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.chobitech.lib.android.FireSwitch
import com.chobitech.lib.android.WithActivityResult
import com.chobitech.lib.android.findActivity


@Composable
fun WithPermissionCheck(
    permissions: Array<String>,
    onResult: (Map<String, PermissionCheckResult>) -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) = WithActivityResult(
    args = permissions,
    contractGenerator = { ActivityResultContracts.RequestMultiplePermissions() },
    onResult = { context, resMap ->
        val activity = context.findActivity() ?: return@WithActivityResult

        val rMap = resMap.mapValues { (p, isGranted) ->
            when (isGranted) {
                true -> PermissionCheckResult.GRANTED
                false -> when (activity.shouldShowRequestPermissionRationale(p)) {
                    true -> PermissionCheckResult.DENIED
                    false -> PermissionCheckResult.PERMANENTLY_DENIED
                }
            }
        }

        onResult(rMap)
    },
    content = content
)
