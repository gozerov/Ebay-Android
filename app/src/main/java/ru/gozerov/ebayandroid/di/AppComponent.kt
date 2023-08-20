package ru.gozerov.ebayandroid.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gozerov.ebayandroid.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        fun build() : AppComponent

        @BindsInstance
        fun context(context: Context) : Builder

    }

}