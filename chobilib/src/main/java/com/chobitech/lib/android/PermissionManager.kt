package com.chobitech.lib.android

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.compose.LocalLifecycleOwner


enum class PermissionCheckResult(val value: Int) {
    GRANTED(0),
    DENIED(1),
    PERMANENTLY_DENIED(2);

    companion object {
        fun from(value: Int): PermissionCheckResult = entries.find { it.value == value } ?: throw IllegalArgumentException("Invalid value: $value")

    }
}



@Composable
fun WithPermissionManager(
    key: String,
    content: @Composable (permissionManager: PermissionManager) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = context.findComponentActivity() ?: return

    val permissionManager = remember {
        PermissionManager(key, activity.activityResultRegistry, context.applicationContext)
    }

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(permissionManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(permissionManager)
        }
    }

    content(permissionManager)
}


class PermissionManager(
    val key: String,
    private val registry: ActivityResultRegistry,
    private val context: Context
) : DefaultLifecycleObserver {

    companion object {
        fun checkPermission(context: Context, permission: String): PermissionCheckResult {
            return when (ContextCompat.checkSelfPermission(context, permission)) {
                PackageManager.PERMISSION_GRANTED -> PermissionCheckResult.GRANTED
                else -> PermissionCheckResult.DENIED
            }
        }

        fun getDeniedType(activity: Activity, permission: String): PermissionCheckResult {
            return when (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                true -> PermissionCheckResult.DENIED
                false -> PermissionCheckResult.PERMANENTLY_DENIED
            }
        }
    }

    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>




}


/*
package com.chobitech.app.numberpatternblocker.controllers.permissions

import ads_mobile_sdk.ch
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.chobitech.app.numberpatternblocker.NpbSub
import com.chobitech.app.numberpatternblocker.findActivity

class NpbPermissionManager(
    private val registry: ActivityResultRegistry,
    private val context: Context
) : DefaultLifecycleObserver {

    companion object {
        private const val PERMISSION_CHECK_KEY = "npb_permission_check"

        const val GRANTED = 0
        const val DENIED = 1
        const val PERMANENTLY_DENIED = 2

        val requiredPermissions = arrayListOf<String>().also {
            if (NpbSub.isApi33Plus) {
                it.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }.toTypedArray()

        fun isNotificationOK(context: Context): Boolean =
            when {
                NpbSub.isApi33Plus -> checkPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == GRANTED
                else -> true
            }

        fun checkPermission(context: Context, permission: String): Int {
            return when (ContextCompat.checkSelfPermission(context, permission)) {
                PackageManager.PERMISSION_GRANTED -> GRANTED
                else -> DENIED
            }
        }

        fun getDeniedType(activity: Activity, permission: String): Int {
            return when (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                true -> DENIED
                false -> PERMANENTLY_DENIED
            }
        }


        @Composable
        fun WithPermissionManager(
            content: @Composable (permissionManager: NpbPermissionManager) -> Unit
        ) {
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current
            val activity = context as? ComponentActivity ?: return

            val permissionManager = remember {
                NpbPermissionManager(activity.activityResultRegistry, context.applicationContext)
            }

            DisposableEffect(lifecycleOwner) {
                lifecycleOwner.lifecycle.addObserver(permissionManager)

                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(permissionManager)
                }
            }

            content(permissionManager)
        }
    }


    private class PermissionCheckedData(
        val targetPermissions: Array<String>,
        val onResult: (Map<String, Int>) -> Unit
    ) {
        val resultMap = hashMapOf<String, Int>()
        val checkTargets = arrayListOf<String>()

        fun checkPermissions(context: Context) {
            val appCon = context.applicationContext

            resultMap.clear()
            checkTargets.clear()

            targetPermissions.forEach { p ->
                when (ContextCompat.checkSelfPermission(appCon, p) == PackageManager.PERMISSION_GRANTED) {
                    true -> resultMap[p] = GRANTED
                    false -> checkTargets.add(p)
                }
            }
        }
    }



    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>

    private var checkedData: PermissionCheckedData? = null

    override fun onCreate(owner: LifecycleOwner) {
        requestPermissionsLauncher = registry.register(
            PERMISSION_CHECK_KEY,
            owner,
            ActivityResultContracts.RequestMultiplePermissions()
        ) { resMap ->
            if (checkedData == null) {
                return@register
            }

            val activity = context.findActivity()

            resMap.forEach { (p, res) ->
                checkedData!!.resultMap[p] = when (res) {
                    true -> GRANTED
                    else -> when (activity) {
                        null -> DENIED
                        else -> getDeniedType(activity, p)
                    }
                }
            }

            checkedData!!.onResult(checkedData!!.resultMap)

            checkedData = null
        }
    }

    fun checkAndRequestRequiredPermissions(
        permissions: Array<String>,
        onResult: (resMap: Map<String, Int>) -> Unit
    ) {
        if (!::requestPermissionsLauncher.isInitialized || checkedData != null) {
            return
        }

        checkedData = PermissionCheckedData(permissions, onResult).also { cData ->
            cData.checkPermissions(context)
        }

        when (checkedData!!.checkTargets.isNotEmpty()) {
            true -> requestPermissionsLauncher.launch(checkedData!!.checkTargets.toTypedArray())
            false -> {
                checkedData!!.onResult(checkedData!!.resultMap)
                checkedData = null
            }
        }
    }
}
 */