package ru.gozerov.presentation.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.gozerov.presentation.screens.home.ComposableFragment
import ru.gozerov.presentation.screens.home.home_page.HomePageFragment
import ru.gozerov.presentation.screens.home.product_details.ProductDetailsFragment
import ru.gozerov.presentation.screens.home.search.SearchProductFragment
import ru.gozerov.presentation.screens.login.enter_new_password.EnterNewPasswordFragment
import ru.gozerov.presentation.screens.login.reset.ResetPasswordFragment
import ru.gozerov.presentation.screens.login.sign_in.SignInFragment
import ru.gozerov.presentation.screens.login.sign_up.account_data.SetAccountDataFragment
import ru.gozerov.presentation.screens.login.sign_up.email.RegisterAccountFragment
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION
import ru.gozerov.presentation.screens.tabs.TabsFragment

interface AppNavigationProvider {

    val cicerone: Cicerone<Router>

    fun setNavigator(activity: FragmentActivity, containerId: Int) {
        cicerone.getNavigatorHolder().setNavigator(AppNavigator(activity, containerId))
    }

    fun removeNavigator() {
        cicerone.getNavigatorHolder().removeNavigator()
    }

    fun getRouter() : Router = cicerone.router

    @Composable
    fun Navigation()

}

fun Context.findNavigationProvider() : AppNavigationProvider {
    return this.applicationContext as AppNavigationProvider
}

fun Fragment.findNavigationProvider() : AppNavigationProvider {
    return this.requireContext().findNavigationProvider()
}

@Composable
fun Fragment.FindComposableNavigation() {
    (this.requireContext().applicationContext as AppNavigationProvider).Navigation()
}

object Screens {
    fun tabs(token: String? = null) = FragmentScreen { TabsFragment.newInstance(token) }

    fun signIn() = FragmentScreen { SignInFragment() }

    fun forgotPassword() = FragmentScreen { ResetPasswordFragment() }

    fun verificationCode(navDestination: NAV_DESTINATION, email: String) = FragmentScreen {
        VerificationCodeFragment.newInstance(navDestination, email)
    }

    fun enterNewPassword() = FragmentScreen { EnterNewPasswordFragment() }

    fun registerAccount() = FragmentScreen { RegisterAccountFragment() }

    fun setAccountData() = FragmentScreen { SetAccountDataFragment() }

    fun homePage() = FragmentScreen { HomePageFragment() }
    fun searchProduct() = FragmentScreen { SearchProductFragment() }

    fun composableNavigation() = FragmentScreen { ComposableFragment() }

    fun productDetails(id: Int) = FragmentScreen { ProductDetailsFragment.newInstance(id) }

}