package ru.gozerov.presentation.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.gozerov.domain.models.Good
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.DialogProductPopUpBinding


@Composable
fun ColumnScope.SearchProduct(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    onSearch: ((String) -> Unit)? = null,
    onEmptyField: (() -> Unit)? = null
) {
    val greyColor = colorResource(id = R.color.grey_light)
    TextField(
        modifier = modifier,
        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
            if (it.text.isNotBlank()) {
                onSearch?.invoke(it.text)
            } else
                onEmptyField?.invoke()
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = greyColor,
            unfocusedContainerColor = greyColor,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch?.invoke(textFieldValue.value.text)
            }
        ),
        singleLine = true,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null,
                tint = colorResource(id = R.color.half_grey),
                modifier = Modifier.clickable {
                    onSearch?.invoke(textFieldValue.value.text)
                }
            )
        },

        placeholder = {
            Text(
                text = "Search Product",
                letterSpacing = TextUnit(0.05f, TextUnitType.Sp),
                color = colorResource(id = R.color.half_grey)
            )
        }
    )

}


@Composable
fun ProductCard(context: Context, product: Good) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(all = 8.dp)
            .width(192.dp)
            .height(308.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                context.findNavigationProvider().getRouter().navigateTo(Screens.productDetails(product.vendorCode))
            }

    ) {
        if (product.images.isNullOrEmpty())
            NoPhoto(modifier = Modifier
                .height(212.dp)
                .padding(horizontal = 36.dp)
                .fillMaxWidth()
            )
        else
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(180.dp),
                model = product.images!!.first(),
                contentDescription = null
            )
        DefaultText(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            text = product.name,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
        DefaultText(
            modifier = Modifier.padding(
                top = 8.dp, start = 12.dp
            ),
            text = "${product.price} $",
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = colorResource(id = R.color.red_price)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                horizontal = 12.dp, vertical = 8.dp
            )
        ) {
            product.rating?.let {
                StarIconFull()
                Spacer(modifier = Modifier.width(4.dp))
                DefaultText(text = it.toString())
                Spacer(modifier = Modifier.width(20.dp))
                DefaultText(
                    text = "${product.reviews?.size ?: 0} Reviews",
                    fontSize = 12.sp
                )
            } ?: DefaultText(
                text = "No Reviews",
                fontSize = 12.sp
            )
            Box(
                modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_more_vert_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            val dialogBinding =
                                DialogProductPopUpBinding.inflate(LayoutInflater.from(context))
                            val dialog = AlertDialog
                                .Builder(context)
                                .setView(dialogBinding.root)
                                .setCancelable(true)
                                .create()
                            val width =
                                context.resources.getDimensionPixelOffset(R.dimen.dialog_width)
                            dialogBinding.closeDialog.setOnClickListener {
                                dialog.cancel()
                            }
                            dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                            dialog.show()
                            dialog.window?.setLayout(
                                width,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        }
                )
            }

        }
    }
}