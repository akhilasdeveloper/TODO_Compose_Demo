package com.akhilasdeveloper.todocomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.akhilasdeveloper.todocomposedemo.ui.screens.TodoScreen
import com.akhilasdeveloper.todocomposedemo.ui.theme.TODOComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TODOComposeDemoTheme {
                TodoScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
