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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ugisozols.fancycar.presentation.map_screen.MapScreen
import com.ugisozols.fancycar.presentation.owner_list_screen.OwnersListScreen
import com.ugisozols.fancycar.presentation.ui.theme.FancyCarTheme
import com.ugisozols.fancycar.presentation.welcome_screen.WelcomeScreen
import com.ugisozols.fancycar.util.navigation.Route
import com.ugisozols.fancycar.util.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()

            FancyCarTheme {
                Scaffold(
                    Modifier.fillMaxSize(),
                    scaffoldState
                ) {
                    NavHost(navController = navController, startDestination = Route.WELCOME_PAGE) {
                        composable(Route.WELCOME_PAGE) {
                            WelcomeScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.OWNER_LIST_PAGE) {
                            OwnersListScreen(
                                scaffoldState = scaffoldState,
                                navController::navigate
                            )
                        }
                        composable(
                            Route.MAP_SCREEN + "/{ownerId}",
                            arguments = listOf(
                                navArgument("ownerId"){
                                    type = NavType.IntType
                                    defaultValue = 1
                                }
                            )
                        ){
                            MapScreen(
                                scaffoldState = scaffoldState,
                                ownerId = it.arguments?.getInt("ownerId"),
                                navController::navigate
                            )
                        }

                    }
                }

            }
        }
    }
}

