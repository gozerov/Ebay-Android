package ru.gozerov.ebayandroid.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gozerov.presentation.activity.MainActivity
import ru.gozerov.presentation.utils.DependenciesContainer
import ru.gozerov.presentation.utils.MultiViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : DependenciesContainer {

    override val viewModelFactory: MultiViewModelFactory
    override fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        fun build() : AppComponent

        @BindsInstance
        fun context(context: Context) : Builder

    }

}