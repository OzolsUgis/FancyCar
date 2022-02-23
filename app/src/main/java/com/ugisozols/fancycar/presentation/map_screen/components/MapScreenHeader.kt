package com.ugisozols.fancycar.presentation.map_screen.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.presentation.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MapScreenHeader(
    owner: CarOwner?,
    isVisible : Boolean,
    onClick : () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Surface(
        elevation = 50.dp,
        shape = RoundedCornerShape(
            bottomStart = 40.dp,
            bottomEnd = 40.dp
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = spacing.spacingMedium,
                    end = spacing.spacingMedium,
                    top = spacing.spacingExtraLarge,
                    bottom = spacing.spacingMedium
                )
                .background(
                    HeadingColor,
                    RoundedCornerShape(
                        bottomStart = 70.dp,
                        bottomEnd = 70.dp
                    )
                )
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onClick() },
                        modifier
                            .width(80.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(BackgroundColor)
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = null
                            )
                            Text(text = "Back", color = HeadingColor)
                        }

                    }
                    Spacer(modifier = Modifier.width(spacing.spacingLarge))
                    Text(
                        text = owner?.name.orEmpty(),
                        fontFamily = quicksand,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BackgroundColor
                    )
                    Spacer(modifier = Modifier.width(spacing.spacingSmall))
                    Text(
                        text = owner?.surname.orEmpty(),
                        fontFamily = quicksand,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BackgroundColor
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spacingLarge))
                AnimatedVisibility(visible = isVisible) {
                    content()
                }

            }

        }
    }

}