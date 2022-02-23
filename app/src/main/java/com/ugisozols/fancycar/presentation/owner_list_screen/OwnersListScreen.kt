package com.ugisozols.fancycar.presentation.owner_list_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ugisozols.fancycar.presentation.owner_list_screen.components.ExpendableDriver
import com.ugisozols.fancycar.presentation.owner_list_screen.components.ListScreenHeader
import com.ugisozols.fancycar.presentation.owner_list_screen.components.VehicleListItem
import com.ugisozols.fancycar.presentation.ui.theme.*
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.UiText
import com.ugisozols.fancycar.util.navigation.Route
import kotlinx.coroutines.flow.collect

@Composable
fun OwnersListScreen(
    scaffoldState: ScaffoldState,
    onNavigate : (UiEvent.Navigate) -> Unit,
    viewModel: OwnerListScreenViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                color = BackgroundColor
            )
            .padding(bottom = spacing.spacingLarge)

    ) {
        ListScreenHeader(
            Modifier
                .fillMaxSize(),
            "Choose the best driver"
        )
        Column {
            Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))
            Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))
            Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))
            Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))
            Spacer(modifier = Modifier.height(spacing.spacingLarge))
            LazyColumn(){
                items(viewModel.state.value.ownersList) { owner ->
                    ExpendableDriver(
                        owner = owner ,
                        onToggleClick = { owner.isExpanded = !owner.isExpanded },
                        content = {
                          Column(
                              Modifier
                                  .fillMaxHeight()
                                  .padding(
                                      horizontal = spacing.spacingLarge,
                                      vertical = spacing.spacingMedium
                                  )
                                  .fillMaxWidth()
                                  .background(ObjectColor)

                          ) {
                             owner.vehicles.forEach { vehicle ->
                                 VehicleListItem(
                                    vehicles = vehicle,
                                     onNavigate = {onNavigate(UiEvent.Navigate(Route.MAP_SCREEN + "/${owner.id}"))}
                                 )
                             }
                          }
                        },
                        modifier = Modifier.wrapContentHeight()
                    )
                }
            }
        }
        if(state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomCenter),
                color = HeadingColor,
                strokeWidth = 5.dp,
            )
        }
    }

}

