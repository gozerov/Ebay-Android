package ru.gozerov.presentation.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.home.HomePageFragment
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment
import ru.gozerov.presentation.screens.login.sign_in.SignInFragment
import ru.gozerov.presentation.screens.tabs.TabsFragment
import ru.gozerov.presentation.utils.NavigatorType.GLOBAL

interface AppNavigationProvider {

    val cicerone: Cicerone<Router>

    fun setNavigator(activity: FragmentActivity, type: NavigatorType) {
        val navigator: AppNavigator = when(type) {
            GLOBAL -> {
                AppNavigator(activity, GlobalContainerId)
            }
            else -> {
                AppNavigator(activity, TabsContainerId)
            }
        }
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    fun removeNavigator() {
        cicerone.getNavigatorHolder().removeNavigator()
    }

    fun getRouter() : Router = cicerone.router

    companion object {
        val GlobalContainerId = R.id.fragmentContainerEmpty
        val TabsContainerId = R.id.fragmentContainerTabs
    }

}

enum class NavigatorType {
    GLOBAL, TABS, HOME, WISHLIST, ORDER, ACCOUNT
}

fun Context.findNavigationProvider() : AppNavigationProvider {
    return this.applicationContext as AppNavigationProvider
}

fun Fragment.findNavigationProvider() : AppNavigationProvider {
    return this.requireContext().findNavigationProvider()
}

object Screens {

    fun tabs() = FragmentScreen { TabsFragment() }

    fun signIn() = FragmentScreen { SignInFragment() }
    fun verificationCode() = FragmentScreen { VerificationCodeFragment() }

    fun homePage() = FragmentScreen { HomePageFragment() }

}