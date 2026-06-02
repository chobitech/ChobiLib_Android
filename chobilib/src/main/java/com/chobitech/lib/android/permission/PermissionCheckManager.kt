//package com.chobitech.lib.android.permission
//
//import androidx.activity.ComponentActivity
//import androidx.activity.result.contract.ActivityResultContracts
//import com.chobitech.lib.android.isAvailable
//
//class PermissionCheckManager(
//    private val activity: ComponentActivity
//) {
//
//    private data class CheckInfoHolder(
//        val resultMap: HashMap<String, PermissionCheckResult> = hashMapOf(),
//        val checkTargets: ArrayList<String> = arrayListOf(),
//        val onResult: ((Map<String, PermissionCheckResult>) -> Unit)
//    )
//
//    private var checkInfoHolder: CheckInfoHolder? = null
//
//    private val appContext = activity.applicationContext
//
//    val requestLauncher = activity.registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { result ->
//        if (!activity.isAvailable) {
//            return@registerForActivityResult
//        }
//
//        checkInfoHolder?.apply {
//
//            val rMap = result.mapValues { (p, isGranted) ->
//                when (isGranted) {
//                    true -> PermissionCheckResult.GRANTED
//                    false -> when (activity.shouldShowRequestPermissionRationale(p)) {
//                        true -> PermissionCheckResult.DENIED
//                        false -> PermissionCheckResult.PERMANENTLY_DENIED
//                    }
//                }
//            }
//
//            resultMap.putAll(rMap)
//            onResult(resultMap)
//        }
//
//        checkInfoHolder = null
//    }
//
//
//
//}