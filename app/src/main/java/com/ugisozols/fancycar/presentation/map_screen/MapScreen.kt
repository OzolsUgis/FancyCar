package com.ugisozols.fancycar.presentation.map_screen

import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.model.LatLng
import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.presentation.map_screen.components.GetPermission
import com.ugisozols.fancycar.presentation.map_screen.components.GoogleMapView
import com.ugisozols.fancycar.presentation.map_screen.components.MapScreenHeader
import com.ugisozols.fancycar.presentation.map_screen.components.VehicleItem
import com.ugisozols.fancycar.presentation.ui.theme.ContentColor
import com.ugisozols.fancycar.presentation.ui.theme.HeaderObject
import com.ugisozols.fancycar.presentation.ui.theme.HeadingColor
import com.ugisozols.fancycar.presentation.ui.theme.LocalSpacing
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.UiText
import com.ugisozols.fancycar.util.navigation.Route
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MapScreen(
    scaffoldState: ScaffoldState,
    ownerId: Int?,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MapScreenViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val isMapLoaded = viewModel.isMapLoaded.value
    val state = viewModel.state.value
    viewModel.deviceCurrentLocation.value
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    viewModel.getDeviceLocation(context)


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
            val handler = Handler(Looper.getMainLooper())
            val runnable = object : Runnable {
                override fun run() {
                    viewModel.onOwnerVehicleUpdate(ownerId!!)
                    // 60000 - 1 minute
                    handler.postDelayed(this,60000)
                }
            }
            val observer = LifecycleEventObserver { _, event ->
                when(event){
                    Lifecycle.Event.ON_START -> {
                        handler.post(runnable)
                    }
                    Lifecycle.Event.ON_PAUSE ->{
                        handler.removeCallbacks(runnable)
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

    )

    LaunchedEffect(key1 = true){

    }

    GetPermission(
        lifecycleOwner = LocalLifecycleOwner.current,
        onPermissionGranted = {
            viewModel.onPermissionGranted()
        },
        onIsPermanentlyDenied = {

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
            polypoints = viewModel.polylines.value,
            onMarkerClick = { vehicle ->
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
            onClick = { onNavigate(UiEvent.Navigate(Route.OWNER_LIST_PAGE)) },
            content = {
                VehicleItem(vehicles = viewModel.clickedMarkerVehicle.value!!)
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spacingMedium)
        ,
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (viewModel.isExtensionVisible.value) {

                Button(
                    onClick = {
                        viewModel.setSelectButton(true)
                        viewModel.getPolylines()
                        viewModel.isExtensionVisible.value = false
                    },
                    elevation = ButtonDefaults.elevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = HeaderObject,
                        contentColor = ContentColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(70.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.select).uppercase()
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))
                Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))

            }

        }


    }


}