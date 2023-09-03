package ru.gozerov.presentation.utils

interface ToolbarHolder {

    fun onToolbarChange(type: ToolbarType, label: String)

    enum class ToolbarType {
        NONE, NAV_UP_ONLY
    }

}
