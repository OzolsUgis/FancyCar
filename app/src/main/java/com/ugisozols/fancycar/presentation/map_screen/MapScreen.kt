package com.ugisozols.fancycar.presentation.map_screen

import android.provider.ContactsContract
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.model.LatLng
import com.ugisozols.fancycar.presentation.map_screen.components.GetPermission
import com.ugisozols.fancycar.presentation.map_screen.components.GoogleMapView
import com.ugisozols.fancycar.presentation.map_screen.components.MapScreenHeader
import com.ugisozols.fancycar.presentation.map_screen.components.VehicleItem
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.navigation.Route
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MapScreen(
    scaffoldState : ScaffoldState,
    ownerId : Int?,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MapScreenViewModel = hiltViewModel()
) {

    val isMapLoaded = viewModel.isMapLoaded.value
    val state = viewModel.state.value
    viewModel.deviceCurrentLocation.value
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var permissions = remember {
        mutableStateOf(false)
    }

    viewModel.getDeviceLocation(context)

    Log.d("My_Tag_device", viewModel.deviceCurrentLocation.value.toString())

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if(event == Lifecycle.Event.ON_START){
                    viewModel.onOwnerVehicleUpdate(ownerId !!)
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

    )

    GetPermission(
        lifecycleOwner = LocalLifecycleOwner.current,
        onPermissionGranted = {
            viewModel.onPermissionGranted()
            permissions.value = true
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
            myLocation = viewModel.deviceCurrentLocation.value,
            onMapLoaded = { viewModel.onMapLoaded() },
            onMarkerClick = { vehicle ->
                Log.d("test", vehicle.toString())
                viewModel.setClickedMarkerVehicle(vehicle)
                viewModel.onExtensionVisibleChange()
            },
            modifier = Modifier.fillMaxHeight(0.90f)
        )
        if (!isMapLoaded || !permissions.value) {
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
            onClick = { onNavigate(UiEvent.Navigate(Route.OWNER_LIST_PAGE)) },
            content = {
                VehicleItem(vehicles = viewModel.clickedMarkerVehicle.value!!)
            }
        )
    }
}