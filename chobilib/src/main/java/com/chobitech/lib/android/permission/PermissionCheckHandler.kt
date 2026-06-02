package com.chobitech.lib.android.permission

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.chobitech.lib.android.FireSwitch
import com.chobitech.lib.android.WithActivityResultHandler
import com.chobitech.lib.android.findActivity


@Composable
fun WithPermissionCheckHandler(
    permissions: Array<String>,
    onResult: (Map<String, PermissionCheckResult>) -> Unit,
    content: @Composable (fireSwitch: FireSwitch) -> Unit
) = WithActivityResultHandler(
    contractGenerator = { ActivityResultContracts.RequestMultiplePermissions() },
    onResult = { context, resMap ->
        val activity = context.findActivity() ?: return@WithActivityResultHandler

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
    content = { launcher ->
        val scope = rememberCoroutineScope()
        val fireSwitch = remember(scope) {

            FireSwitch(scope)
        }

        LaunchedEffect(fireSwitch.fired) {
            fireSwitch.fired.collect {
                launcher.launch(permissions)
            }
        }

        content(fireSwitch)
    }
)

//package com.chobitech.lib.android.permission
//
//import androidx.activity.ComponentActivity
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.platform.LocalContext
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import com.chobitech.lib.android.ActivityResultHandler
//import com.chobitech.lib.android.DebugLog
//import com.chobitech.lib.android.isAvailable
//import com.chobitech.lib.android.isPermissionOk
//
//
//class PermissionCheckHandler(
//    key: String,
//    val permissions: Array<String>,
//    private val activity: ComponentActivity
//) : ActivityResultHandler<Array<String>, Map<String, Boolean>>(
//    key = key,
//    registry = activity.activityResultRegistry,
//    contractGenerator = { ActivityResultContracts.RequestMultiplePermissions() },
//) {
//
//    private class CheckStateHolder(
//        val result: HashMap<String, PermissionCheckResult> = hashMapOf(),
//        val checkTargets: ArrayList<String> = arrayListOf(),
//        val onResult: (Map<String, PermissionCheckResult>) -> Unit
//    )
//
//    private var stateHolder: CheckStateHolder? = null
//
//    private val appContext = activity.applicationContext
//
//
//    suspend fun checkAndRequestPermissions(
//        onResult: (Map<String, PermissionCheckResult>) -> Unit
//    ) {
//        if (stateHolder != null) {
//            return
//        }
//
//        val holder = CheckStateHolder(onResult = onResult).also {
//            permissions.forEach { p ->
//                when (appContext.isPermissionOk(p)) {
//                    true -> it.result[p] = PermissionCheckResult.GRANTED
//                    false -> it.checkTargets.add(p)
//                }
//            }
//            stateHolder = it
//        }
//
//        if (holder.checkTargets.isNotEmpty()) {
//            execute(
//                holder.checkTargets.toTypedArray(),
//
//            ) { resMap ->
//                if (!activity.isAvailable || stateHolder == null) {
//                    return@execute
//                }
//
//                try {
//                    val rMap = resMap.mapValues { (p, isGranted) ->
//                        when (isGranted) {
//                            true -> PermissionCheckResult.GRANTED
//                            false -> when (activity.shouldShowRequestPermissionRationale(p)) {
//                                true -> PermissionCheckResult.DENIED
//                                false -> PermissionCheckResult.PERMANENTLY_DENIED
//                            }
//                        }
//                    }
//                    holder.result.putAll(rMap)
//                    holder.onResult(holder.result)
//                } catch (e: Exception) {
//                    DebugLog.e(e)
//                } finally {
//                    stateHolder = null
//                }
//            }
//        } else {
//            try {
//                onResult(holder.result)
//            } catch (e: Exception) {
//                DebugLog.e(e)
//            } finally {
//                stateHolder = null
//            }
//        }
//
//    }
//}
//
//
//@Composable
//fun WithPermissionCheckHandler(
//    key: String,
//    permissions: Array<String>,
//    content: @Composable (handler: PermissionCheckHandler) -> Unit
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val activity = context as? ComponentActivity ?: return
//
//    val handler = remember(
//        key,
//        permissions,
//        activity
//    ) {
//        PermissionCheckHandler(
//            key,
//            permissions,
//            activity
//        )
//    }
//
//    DisposableEffect(
//        key,
//        permissions,
//        activity
//    ) {
//        lifecycleOwner.lifecycle.addObserver(handler)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(handler)
//        }
//    }
//
//    content(handler)
//}
//
