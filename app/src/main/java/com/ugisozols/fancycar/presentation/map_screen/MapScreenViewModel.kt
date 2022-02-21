package com.ugisozols.fancycar.presentation.map_screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.domain.use_cases.UpdateOwnersVehicles
import com.ugisozols.fancycar.util.Resource
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.http2.Http2Reader
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val useCase : UpdateOwnersVehicles
) : ViewModel() {
    private val _state = derivedStateOf {
        mutableStateOf(MapScreenState(null))
    }
    val state: State<MapScreenState> = _state.value

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    private var job: Job? = null

    fun resetJob(ownerId: Int){
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable{
            override fun run() {
                onOwnerVehicleUpdate(1)
                Log.d("MY_APP", "This should show when function is called")
                handler.postDelayed(this, 5000L)
            }
        }, 0)
    }

    fun onOwnerVehicleUpdate(ownerId : Int) {
        job?.cancel()
        job = viewModelScope.launch {
            useCase(ownerId).collect{ result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value.value = state.value.copy(
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
                        _state.value.value = state.value.copy(
                            Owner = result.data,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value.value = state.value.copy(
                            Owner = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}