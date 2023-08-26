package ru.gozerov.presentation.utils

import android.content.Context
import ru.gozerov.presentation.activity.MainActivity

val Context.appComponent : DependenciesContainer
    get() = (this.applicationContext as DependenciesProvider).get()

interface DependenciesContainer {

    fun inject(activity: MainActivity)

}

interface DependenciesProvider {

    fun get() : DependenciesContainer

}
