package ru.gozerov.presentation.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    end: Dp = 24.dp
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