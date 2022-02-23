package com.ugisozols.fancycar.presentation.map_screen.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.domain.model.OwnerVehicles


@Composable
fun GoogleMapView(
    vehicles: List<OwnerVehicles>?,
    myLocation: LatLng,
    onMapLoaded: () -> Unit,
    onMarkerClick: (vehicle: OwnerVehicles) -> Unit,
    modifier: Modifier = Modifier
){

    val cameraPositionState = rememberCameraPositionState(){
        position = CameraPosition.fromLatLngZoom(
            // Riga
            myLocation,
            13f
        )
    }
    val bounds = LatLngBounds.builder()

    val markerClick: (Marker, OwnerVehicles) -> Boolean = {_, vehicle ->
        onMarkerClick(vehicle)
        false
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.NORMAL),
        uiSettings = MapUiSettings(compassEnabled = false),
        onMapLoaded = {
            onMapLoaded()
        },
        googleMapOptionsFactory = {
            GoogleMapOptions().camera(
                CameraPosition.fromLatLngZoom(
                    LatLng(56.946285,24.105078),
                    13f
                )
            )
        },

    ){
        vehicles?.forEach { vehicle ->

            Marker(
                position = LatLng(vehicle.latitude,vehicle.longitude),
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_red_car),
                tag = vehicle,
                onClick = {
                    markerClick(it, it.tag as OwnerVehicles)
                }
            )
            bounds.include(LatLng(vehicle.latitude,vehicle.longitude))

        }

    }



}