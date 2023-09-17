package ru.gozerov.presentation.utils

import androidx.annotation.StyleRes

typealias ToolbarAction = () -> Unit

interface ToolbarHolder {

    fun onToolbarChange(actions: Map<ActionType, ToolbarAction?>, label: String, @StyleRes textAppearance: Int?)

    enum class ActionType {
        NONE, NAV_UP, NOTIFICATION, CART
    }

}
