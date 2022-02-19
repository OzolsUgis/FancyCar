package com.ugisozols.fancycar.presentation.welcome_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.presentation.ui.theme.ButtonColor
import com.ugisozols.fancycar.presentation.ui.theme.ButtonHeight
import com.ugisozols.fancycar.presentation.ui.theme.ContentColor
import com.ugisozols.fancycar.presentation.ui.theme.LocalSpacing
import com.ugisozols.fancycar.util.UiEvent
import com.ugisozols.fancycar.util.navigation.Route


@Composable
fun WelcomeScreen(
    onNavigate : (UiEvent.Navigate) -> Unit
) {
    val spacing = LocalSpacing.current
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.welcome_photo),
            contentDescription = stringResource(id = R.string.welcome_photo_desc),
            contentScale = ContentScale.Crop
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxHeight()
                .padding(
                    start = spacing.spacingMedium,
                    end = spacing.spacingMedium,
                    top = spacing.spacingExtraLarge,
                    bottom = spacing.spacingLarge
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))
                Text(
                    text = stringResource(id = R.string.find_driver),
                    style = MaterialTheme.typography.h1
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(id = R.string.awesome_car),
                    style = MaterialTheme.typography.h2
                )
                Spacer(modifier = Modifier.height(spacing.spacingLarge))
                Button(
                    modifier = Modifier.height(ButtonHeight),
                    onClick = {onNavigate(UiEvent.Navigate(Route.OWNER_LIST_PAGE))},
                    shape = RoundedCornerShape(100.dp),
                    elevation = ButtonDefaults.elevation(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ButtonColor,
                        contentColor = ContentColor
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = stringResource(id = R.string.get_started),
                            style = MaterialTheme.typography.body1
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = stringResource(
                                id = R.string.arrow
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing.spacingLarge))
                Text(
                    text = stringResource(id = R.string.start_journey),
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = Modifier.height(spacing.spacingExtraLarge))
            }
        }
    }
}