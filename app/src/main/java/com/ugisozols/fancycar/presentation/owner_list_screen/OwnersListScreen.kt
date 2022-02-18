package com.ugisozols.fancycar.presentation.owner_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugisozols.fancycar.presentation.ui.theme.LocalSpacing
import com.ugisozols.fancycar.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun OwnersListScreen(
    scaffoldState: ScaffoldState,
    viewModel: OwnerListScreenViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Text(text = state.ownersList.toString())
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}
