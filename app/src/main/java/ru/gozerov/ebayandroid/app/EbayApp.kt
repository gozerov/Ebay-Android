package ru.gozerov.ebayandroid.app

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import ru.gozerov.ebayandroid.di.AppComponent
import ru.gozerov.ebayandroid.di.DaggerAppComponent
import ru.gozerov.presentation.utils.AppNavigationProvider
import ru.gozerov.presentation.utils.DependenciesContainer
import ru.gozerov.presentation.utils.DependenciesProvider

class EbayApp: Application(), DependenciesProvider, AppNavigationProvider {

    override lateinit var cicerone: Cicerone<Router>

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        cicerone = Cicerone.create()
    }

    override fun get(): DependenciesContainer = appComponent

}