package com.ugisozols.fancycar.presentation.map_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.domain.repository.CarOwnerRepository
import com.ugisozols.fancycar.domain.use_cases.GetOwners
import com.ugisozols.fancycar.util.Resource
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val repository: CarOwnerRepository
) : ViewModel() {
    private val _state = mutableStateOf(MapScreenState(null))
    val state: State<MapScreenState> = _state

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    private var job: Job? = null

    init {
        onUpdateList()
    }

    private fun onUpdateList() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getVehicleLocation(3).collect{ result ->
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
}