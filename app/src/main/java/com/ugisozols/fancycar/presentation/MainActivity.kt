package com.ugisozols.fancycar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import com.ugisozols.fancycar.presentation.owner_list_screen.OwnersListScreen
import com.ugisozols.fancycar.presentation.ui.theme.FancyCarTheme
import com.ugisozols.fancycar.presentation.welcome_screen.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                Modifier.fillMaxSize(),
                scaffoldState
            ) {
                OwnersListScreen(scaffoldState = scaffoldState)
            }


        }
    }
}

