package com.ugisozols.fancycar.presentation.map_screen.components


import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.android.gms.maps.model.LatLng
import com.ugisozols.fancycar.domain.model.OwnerVehicles
import com.ugisozols.fancycar.presentation.map_screen.MapScreenViewModel
import com.ugisozols.fancycar.presentation.ui.theme.LocalSpacing

@Composable
fun VehicleItem(
    vehicles: OwnerVehicles,
    modifier : Modifier = Modifier,
    viewModel: MapScreenViewModel = hiltViewModel()
){
    viewModel.decodeCoordinatesToAddress(LatLng(vehicles.latitude,vehicles.longitude))
    viewModel.decodeVehicleColor(vehicles.color )

    val spacing = LocalSpacing.current
    Box(modifier = modifier.wrapContentHeight()){
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberImagePainter(
                    data = vehicles.foto,
                    builder = {
                        transformations(
                            CircleCropTransformation()
                        )
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(spacing.spacingMedium))
            Column(Modifier.fillMaxWidth()) {
                Row {
                    Text(text = "Vehicle: ")
                    Spacer(modifier = Modifier.width(spacing.spacingSmall))
                    Text(
                        text = "${vehicles.make} ${vehicles.model}",
                        fontWeight = FontWeight.Medium
                    )

                }
                Row {
                    Text(text = "Address: ")
                    Spacer(modifier = Modifier.width(spacing.spacingSmall))
                    Text(
                        text = viewModel.decodedAddress.value,
                        fontWeight = FontWeight.Medium
                    )

                }
                Row{
                    Text(text = "Color :")
                    Spacer(modifier = Modifier.width(spacing.spacingSmall))
                    Canvas(modifier = modifier.size(25.dp)){
                        Log.d("VEHICLE_SCREEN", viewModel.vehicleColor.value.toString())

                        drawCircle(
                            color = viewModel.vehicleColor.value,
                            radius = 25f
                        )

                    }
                }
            }
        }
    }
}