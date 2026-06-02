package com.chobitech.lib.kotlin.testapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chobitech.lib.android.permission.WithPermissionCheckHandler
import com.chobitech.lib.kotlin.testapp.ui.theme.ChobiLib_AndroidTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ChobiLib_AndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        WithPermissionCheckHandler(
                            permissions = arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ),
                            onResult = { result ->
                                result.forEach { (string, result) ->

                                }
                            }
                        ) { handler ->
                            Button(
                                onClick = {
                                    handler.exec()
                                }
                            ) {
                                Text("check permission")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChobiLib_AndroidTheme {
        Greeting("Android")
    }
}