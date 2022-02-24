package com.ugisozols.fancycar.presentation.map_screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.domain.model.DeviceLocation
import com.ugisozols.fancycar.domain.model.OwnerVehicles
import com.ugisozols.fancycar.domain.use_cases.DecodeColorFromString
import com.ugisozols.fancycar.domain.use_cases.DecodeCoordinatesToAddress
import com.ugisozols.fancycar.domain.use_cases.UpdateOwnersVehicles
import com.ugisozols.fancycar.util.Resource
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val updateOwnersVehicles : UpdateOwnersVehicles,
    private val decodeCoordinates : DecodeCoordinatesToAddress,
    private val decodeColor : DecodeColorFromString
    ) : ViewModel() {



    private val _state = mutableStateOf(MapScreenState())
    val state: State<MapScreenState> = _state


    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    var isPermissionGranted = mutableStateOf(false)
        private set

    fun onPermissionGranted(){
        isPermissionGranted.value = true
    }

    var decodedAddress = mutableStateOf("")
        private set
    fun decodeCoordinatesToAddress(latLng: LatLng){
        val address = decodeCoordinates(latLng)
        decodedAddress.value = address
    }
    var vehicleColor = mutableStateOf(Color.Black)
        private set

    fun decodeVehicleColor(string: String){
        vehicleColor.value = decodeColor(string)
    }

    var isMapLoaded = mutableStateOf(false)
        private set
    fun onMapLoaded(){
        isMapLoaded.value = true
    }

    var clickedMarkerVehicle = mutableStateOf<OwnerVehicles?>(null)
        private set
    fun setClickedMarkerVehicle(ownerVehicles: OwnerVehicles){
        clickedMarkerVehicle.value = ownerVehicles
    }

    var isExtensionVisible = mutableStateOf(false)
        private set
    fun onExtensionVisibleChange(){
        isExtensionVisible.value = true
    }

    var deviceCurrentLocation = mutableStateOf(LatLng(56.946285,24.105078))
        private set
    fun saveCurrentDeviceLocation(latLng: LatLng){
        deviceCurrentLocation.value = latLng
    }



    private var job: Job? = null


    fun onIsPermanentlyDenied(){
        viewModelScope.launch {
            _event.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.permission_permanently_denied)))
        }
    }

    fun onOwnerVehicleUpdate(ownerId : Int) {
        job?.cancel()
        job = viewModelScope.launch {
            updateOwnersVehicles(ownerId).collect{ result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            Owner = result.data,
                            isLoading = false
                        )
                        _event.send(
                            UiEvent.ShowSnackbar(
                                result.uiText ?: UiText.StringResource(R.string.unknown_error)
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            Owner = result.data,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            Owner = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun getDeviceLocation(context: Context){
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        try {
            if(isPermissionGranted.value){
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(){
                    if (it.isSuccessful){
                        val result = it.result
                        saveCurrentDeviceLocation(
                            LatLng(
                                result.latitude,
                                result.longitude
                            )
                        )
                    }else{
                        Log.d("MY_TAG", "onComplete : location null")
                    }
                }
            }
        }catch (e : SecurityException){
            e.printStackTrace()
        }
    }
}