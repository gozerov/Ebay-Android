package ru.gozerov.presentation.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat
import ru.gozerov.presentation.R

fun ImageView.bindGoodsCategory(name: String) {
    when(name.lowercase()) {
        "food" -> this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.food_32))
        "gift"-> this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.gift_32))
        "fashion"-> this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.fashion_32))
        "gadget"-> this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.gadget_32))
        "computer"-> this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.computer_32))
        "souvenir"-> this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.souvenir_32))
        else -> this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.gadget_32))
    }
}