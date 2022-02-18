package com.ugisozols.fancycar.presentation.owner_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ugisozols.fancycar.domain.use_cases.GetOwners
import com.ugisozols.fancycar.util.Resource
import com.ugisozols.fancycar.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerListScreenViewModel @Inject constructor(
    private val getOwners: GetOwners
) : ViewModel() {
    private val _state = mutableStateOf(OwnerListState())
    val state: State<OwnerListState> = _state

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    private var job: Job? = null

    init {
        onShowList()
    }

    fun onShowList() {
        job?.cancel()
        job = viewModelScope.launch {
            getOwners.invoke().onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            ownersList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _event.send(
                            UiEvent.ShowSnackbar(
                                result.uiText.toString()
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            ownersList = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            ownersList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}