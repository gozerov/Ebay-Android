package ru.gozerov.ebayandroid.app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.HiltAndroidApp
import ru.gozerov.presentation.ProfileScreen
import ru.gozerov.presentation.screens.home.ComposableFragment
import ru.gozerov.presentation.screens.home.home_page.HomePageFragment
import ru.gozerov.presentation.screens.home.product_details.ProductDetailsScreen
import ru.gozerov.presentation.screens.home.search.SearchProductFragment
import ru.gozerov.presentation.screens.login.enter_new_password.EnterNewPasswordFragment
import ru.gozerov.presentation.screens.login.reset.ResetPasswordFragment
import ru.gozerov.presentation.screens.login.sign_in.SignInFragment
import ru.gozerov.presentation.screens.login.sign_up.account_data.SetAccountDataFragment
import ru.gozerov.presentation.screens.login.sign_up.email.RegisterAccountFragment
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION
import ru.gozerov.presentation.screens.tabs.TabsFragment
import ru.gozerov.presentation.utils.AppNavigationProvider
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.findNavigationProvider

@HiltAndroidApp
class EbayApp: Application(), AppNavigationProvider {

    override lateinit var cicerone: Cicerone<Router>

    @Composable
    override fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "productDetails") {
            composable("profile") {
                ProfileScreen()
            }
            composable("productDetails") {
                ProductDetailsScreen()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        cicerone = Cicerone.create()
    }


}