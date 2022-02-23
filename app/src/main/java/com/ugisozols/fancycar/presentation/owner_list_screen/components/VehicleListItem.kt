package com.ugisozols.fancycar.presentation.owner_list_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ugisozols.fancycar.domain.model.OwnerVehicles
import com.ugisozols.fancycar.presentation.ui.theme.HeadingColor
import com.ugisozols.fancycar.presentation.ui.theme.LocalSpacing
import com.ugisozols.fancycar.presentation.ui.theme.quicksand
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.navigation.Route

@Composable
fun VehicleListItem(
    vehicles: OwnerVehicles,
    onNavigate : () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(spacing.spacingSmall)
            .clickable {
                onNavigate()
            }
    ) {
        Row(
            Modifier.fillMaxWidth()
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
            Column(
                modifier.height(80.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = vehicles.make,
                    fontFamily = quicksand,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HeadingColor
                )
                Text(
                    text = vehicles.model,
                    fontFamily = quicksand,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HeadingColor
                )
                Text(
                    text = "Year: ${vehicles.year}",
                    fontFamily = quicksand,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = HeadingColor
                )

                Text(
                    text = "VIN: ${vehicles.vin}",
                    fontFamily = quicksand,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = HeadingColor
                )

            }
        }
    }


}