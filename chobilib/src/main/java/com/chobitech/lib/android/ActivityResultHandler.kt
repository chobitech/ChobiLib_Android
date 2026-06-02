//package com.chobitech.lib.android
//
//import androidx.activity.ComponentActivity
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.ActivityResultRegistry
//import androidx.activity.result.contract.ActivityResultContract
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.platform.LocalContext
//import androidx.lifecycle.DefaultLifecycleObserver
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//open class ActivityResultHandler<I, O>(
//    val key: String,
//    private val registry: ActivityResultRegistry,
//    private val contractGenerator: () -> ActivityResultContract<I, O>,
//) : DefaultLifecycleObserver {
//    lateinit var launcher: ActivityResultLauncher<I>
//        private set
//
//    private var callback: ((result: O) -> Unit)? = null
//
//    fun initialize(owner: LifecycleOwner) {
//        if (::launcher.isInitialized) {
//            return
//        }
//
//        launcher = registry.register(
//            key,
//            owner,
//            contractGenerator()
//        ) { result: O ->
//            callback?.invoke(result)
//            callback = null
//        }
//    }
//
//    suspend fun execute(
//        arg: I,
//        callback: (result: O) -> Unit
//    ) {
//        if (this.callback != null) {
//
//            return
//        }
//
//        this.callback = callback
//        withContext(Dispatchers.Main) {
//            launcher.launch(arg)
//        }
//    }
//
//    override fun onCreate(owner: LifecycleOwner) {
//        initialize(owner)
//    }
//}
//
//@Composable
//fun <I, O> WithActivityResultHandler(
//    key: String,
//    contractGenerator: () -> ActivityResultContract<I, O>,
//    content: @Composable (handler: ActivityResultHandler<I, O>) -> Unit
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val activity = context as? ComponentActivity ?: return
//
//
//    val handler = remember(
//        key,
//        lifecycleOwner,
//        contractGenerator,
//    ) {
//        ActivityResultHandler(
//            key,
//            activity.activityResultRegistry,
//            contractGenerator,
//        )
//    }
//
//    DisposableEffect(
//        key,
//        lifecycleOwner,
//        contractGenerator,
//    ) {
//        lifecycleOwner.lifecycle.addObserver(handler)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(handler)
//        }
//    }
//
//    content(handler)
//}
