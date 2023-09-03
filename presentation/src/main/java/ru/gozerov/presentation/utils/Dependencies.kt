package ru.gozerov.presentation.utils

import android.content.Context
import ru.gozerov.presentation.activity.MainActivity
import ru.gozerov.presentation.screens.login.enter_new_password.EnterNewPasswordFragment
import ru.gozerov.presentation.screens.login.reset.ResetPasswordFragment
import ru.gozerov.presentation.screens.login.sign_in.SignInFragment
import ru.gozerov.presentation.screens.login.sign_up.account_data.SetAccountDataFragment
import ru.gozerov.presentation.screens.login.sign_up.email.RegisterAccountFragment
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment

val Context.appComponent : DependenciesContainer
    get() = (this.applicationContext as DependenciesProvider).get()

interface DependenciesContainer {

    val viewModelFactory: MultiViewModelFactory
    fun inject(activity: MainActivity)
    fun inject(activity: SignInFragment)
    fun inject(resetPasswordFragment: ResetPasswordFragment)
    fun inject(registerAccountFragment: RegisterAccountFragment)
    fun inject(verificationCodeFragment: VerificationCodeFragment)
    fun inject(enterNewPasswordFragment: EnterNewPasswordFragment)
    fun inject(setAccountDataFragment: SetAccountDataFragment)

}

interface DependenciesProvider {

    fun get() : DependenciesContainer

}
