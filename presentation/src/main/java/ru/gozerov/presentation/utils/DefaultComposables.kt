package ru.gozerov.presentation.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.gozerov.presentation.R


@Composable
fun DefaultText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 14.sp,
    color: Color = Color.Black,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        fontSize = fontSize,
        letterSpacing = TextUnit(0.05f, TextUnitType.Sp),
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines
    )
}

@Composable
fun DefaultDivider(horizontal: Dp = 24.dp, vertical: Dp = 24.dp) {
    Divider(
        modifier = Modifier
            .padding(
                horizontal = horizontal,
                vertical = vertical
            ),
        color = colorResource(id = R.color.grey_light)
    )
}
@Composable
fun DefaultDivider(
    top: Dp = 24.dp,
    bottom: Dp = 24.dp,
    start: Dp = 24.dp,
    end: Dp = 24.dp,
    color: Color = colorResource(id = R.color.grey_light)
) {
    Divider(
        modifier = Modifier
            .padding(
                top = top,
                bottom = bottom,
                start = start,
                end = end
            ),
        color = colorResource(id = R.color.grey_light)
    )
}

@Composable
fun StarIconFull(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.baseline_star_16),
        tint = colorResource(id = R.color.yellow_star),
        contentDescription = null
    )
}

@Composable
fun StarIconOutlined(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.baseline_star_outline_16),
        tint = colorResource(id = R.color.half_grey),
        contentDescription = null
    )
}

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Black),
        onClick = onClick
    ) {
        DefaultText(
            modifier = Modifier
                .padding(vertical = 4.dp),
            text = text
        )
    }
}


@Composable
fun FullButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color = colorResource(id = R.color.blue_ocean),
    textColor: Color = Color.White
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        onClick = onClick
    ) {
        DefaultText(
            modifier = Modifier
                .padding(vertical = 4.dp),
            text = text,
            color = textColor
        )
    }
}

@Composable
fun NoPhoto(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.NoPhotography,
        contentDescription = null
    )
}

@Composable
fun RoundedCornerCheckbox(
    label: String? = null,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    size: Float = 24f,
    checkedColor: Color = Color.Blue,
    uncheckedColor: Color = Color.White,
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(
        targetValue = if (isChecked) checkedColor else uncheckedColor,
        label = ""
    )
    val density = LocalDensity.current
    val duration = 200
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(48.dp) // height of 48dp to comply with minimum touch target size
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onValueChange,
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = checkboxColor, shape = shape)
                .border(
                    width = 1.dp,
                    color = if (isChecked) checkedColor else colorResource(id = R.color.half_grey),
                    shape = shape
                ),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) { (size * -0.5).dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration)
                ),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = uncheckedColor
                )
            }
        }
        if (!label.isNullOrBlank()) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = label,
            )
        }
    }
}