package com.ugisozols.fancycar.presentation.map_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.ugisozols.fancycar.presentation.map_screen.components.GetPermission
import com.ugisozols.fancycar.presentation.map_screen.components.GoogleMapView
import com.ugisozols.fancycar.presentation.map_screen.components.MapScreenHeader
import com.ugisozols.fancycar.presentation.map_screen.components.VehicleItem
import com.ugisozols.fancycar.util.UiEvent
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MapScreen(
    scaffoldState : ScaffoldState,
    viewModel: MapScreenViewModel = hiltViewModel()
) {

    val isMapLoaded = viewModel.isMapLoaded.value
    val state = viewModel.state.value
    val context = LocalContext.current
    val vehicleObjectFromMarkerClick =

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }

    GetPermission(
        lifecycleOwner = LocalLifecycleOwner.current,
        onPermissionGranted = {
            viewModel.onPermissionGranted()
        },
        onIsPermanentlyDenied = {
            viewModel.onIsPermanentlyDenied()
        }
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        GoogleMapView(
            vehicles = state.Owner?.vehicles,
            LatLng(56.946285, 24.105078),
            onMapLoaded = { viewModel.onMapLoaded() },
            onMarkerClick = { vehicle ->
                Log.d("test", vehicle.toString())
                viewModel.setClickedMarkerVehicle(vehicle)
                viewModel.onExtensionVisibleChange()
            },
            modifier = Modifier.fillMaxHeight(0.90f)
        )
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier.matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize()
                )
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        MapScreenHeader(
            owner = state.Owner, 
            modifier = Modifier.alpha(1f),
            isVisible = viewModel.isExtensionVisible.value,
            content = {
                VehicleItem(vehicles = viewModel.clickedMarkerVehicle.value!!)
            }
        )
    }
}