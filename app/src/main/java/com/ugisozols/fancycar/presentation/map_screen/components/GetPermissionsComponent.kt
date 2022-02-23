package com.ugisozols.fancycar.presentation.map_screen.components

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ugisozols.fancycar.presentation.map_screen.util.isPermanentlyDenied


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetPermission(
    lifecycleOwner: LifecycleOwner,
    onPermissionGranted : () -> Unit,
    onIsPermanentlyDenied : () -> Unit
){

    val permissionState = rememberMultiplePermissionsState(
        permissions =  listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if(event == Lifecycle.Event.ON_START){
                    permissionState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionState.permissions.forEach {
            when(it.permission){
               Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    when{
                        it.hasPermission -> {
                            onPermissionGranted()
                        }
                        it.isPermanentlyDenied() -> {
                            onIsPermanentlyDenied()
                        }
                    }
               }
            }
        }
    }

}