package ru.gozerov.ebayandroid.app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.HiltAndroidApp
import ru.gozerov.presentation.ProfileScreen
import ru.gozerov.presentation.utils.AppNavigationProvider

@HiltAndroidApp
class EbayApp: Application(), AppNavigationProvider {

    override lateinit var cicerone: Cicerone<Router>

    @Composable
    override fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "profile") {
            composable("profile") {
                ProfileScreen()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        cicerone = Cicerone.create()
    }


}