package ru.gozerov.presentation.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.gozerov.presentation.screens.home.HomePageFragment
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

    fun forgotPassword() = FragmentScreen { ResetPasswordFragment() }

    fun verificationCode(navDestination: NAV_DESTINATION, email: String) = FragmentScreen {
        VerificationCodeFragment.newInstance(navDestination, email)
    }

    fun enterNewPassword() = FragmentScreen { EnterNewPasswordFragment() }

    fun homePage() = FragmentScreen { HomePageFragment() }
    fun registerAccount() = FragmentScreen { RegisterAccountFragment() }
    fun setAccountData() = FragmentScreen { SetAccountDataFragment() }

}