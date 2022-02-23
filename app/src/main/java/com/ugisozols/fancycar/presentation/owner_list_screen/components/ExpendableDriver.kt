package com.ugisozols.fancycar.presentation.owner_list_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.presentation.ui.theme.*
import com.ugisozols.fancycar.util.loadPicture

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpendableDriver(
    owner: CarOwner,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val click : MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(spacing.spacingMedium)
        .clip(RoundedCornerShape(60.dp))
        .background(ObjectColor)
        .clickable {
            click.value = !click.value

        }
    ) {
        Column() {
            Row(
                Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = owner.userPicture,
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
                    Modifier
                        .height(100.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(end = spacing.spacingMedium),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(
                                text = owner.name,
                                fontFamily = quicksand,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HeadingColor
                            )
                            Spacer(modifier = Modifier.width(spacing.spacingSmall))
                            Text(
                                text = owner.surname,
                                fontFamily = quicksand,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HeadingColor
                            )
                        }
                        Icon(
                            imageVector = if (click.value) {
                                Icons.Default.KeyboardArrowUp
                            } else {
                                Icons.Default.KeyboardArrowDown
                            },
                            contentDescription = null,
                            tint = HeadingColor,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                }
            }
            AnimatedVisibility(visible = click.value) {
                content()
            }
        }
    }


}