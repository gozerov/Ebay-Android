package ru.gozerov.ebayandroid.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import ru.gozerov.presentation.screens.home.home_page.HomePageIntent
import ru.gozerov.presentation.screens.home.home_page.HomePageViewModel
import ru.gozerov.presentation.screens.home.home_page.HomePageViewState
import ru.gozerov.presentation.screens.home.product_details.ProductDetailsIntent
import ru.gozerov.presentation.screens.home.product_details.ProductDetailsViewModel
import ru.gozerov.presentation.screens.home.product_details.ProductDetailsViewState
import ru.gozerov.presentation.screens.home.search.SearchProductIntent
import ru.gozerov.presentation.screens.home.search.SearchProductViewModel
import ru.gozerov.presentation.screens.home.search.SearchProductViewState
import ru.gozerov.presentation.screens.home.selected_category.SelectedCategoryIntent
import ru.gozerov.presentation.screens.home.selected_category.SelectedCategoryViewModel
import ru.gozerov.presentation.screens.home.selected_category.SelectedCategoryViewState
import ru.gozerov.presentation.screens.login.enter_new_password.EnterNewPasswordIntent
import ru.gozerov.presentation.screens.login.enter_new_password.EnterNewPasswordViewModel
import ru.gozerov.presentation.screens.login.enter_new_password.EnterNewPasswordViewState
import ru.gozerov.presentation.screens.login.reset.ResetPasswordIntent
import ru.gozerov.presentation.screens.login.reset.ResetPasswordViewModel
import ru.gozerov.presentation.screens.login.reset.ResetPasswordViewState
import ru.gozerov.presentation.screens.login.sign_in.SignInIntent
import ru.gozerov.presentation.screens.login.sign_in.SignInViewModel
import ru.gozerov.presentation.screens.login.sign_in.SignInViewState
import ru.gozerov.presentation.screens.login.sign_up.account_data.SetAccountDataIntent
import ru.gozerov.presentation.screens.login.sign_up.account_data.SetAccountDataViewModel
import ru.gozerov.presentation.screens.login.sign_up.account_data.SetAccountDataViewState
import ru.gozerov.presentation.screens.login.sign_up.email.RegisterAccountIntent
import ru.gozerov.presentation.screens.login.sign_up.email.RegisterAccountViewModel
import ru.gozerov.presentation.screens.login.sign_up.email.RegisterAccountViewState
import ru.gozerov.presentation.screens.login.verification.VerificationCodeIntent
import ru.gozerov.presentation.screens.login.verification.VerificationCodeViewModel
import ru.gozerov.presentation.screens.login.verification.VerificationCodeViewState
import kotlin.reflect.KClass

@InstallIn(SingletonComponent::class)
@Module
interface ViewModelBindModule {

    @Binds
    @[IntoMap ViewModelKey(SignInViewModel::class)]
    fun provideSignInViewModel(signInViewModel: SignInViewModel<SignInIntent, SignInViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(ResetPasswordViewModel::class)]
    fun provideResetPasswordViewModel(resetPasswordViewModel: ResetPasswordViewModel<ResetPasswordIntent, ResetPasswordViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(VerificationCodeViewModel::class)]
    fun provideVerificationCodeViewModel(verificationCodeViewModel: VerificationCodeViewModel<VerificationCodeIntent, VerificationCodeViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(EnterNewPasswordViewModel::class)]
    fun provideEnterNewPasswordViewModel(enterNewPasswordViewModel: EnterNewPasswordViewModel<EnterNewPasswordIntent, EnterNewPasswordViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(RegisterAccountViewModel::class)]
    fun provideRegisterAccountViewModel(registerAccountViewModel: RegisterAccountViewModel<RegisterAccountIntent, RegisterAccountViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(SetAccountDataViewModel::class)]
    fun provideSetAccountDataViewModel(setAccountDataViewModel: SetAccountDataViewModel<SetAccountDataIntent, SetAccountDataViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(HomePageViewModel::class)]
    fun provideHomePageViewModel(homePageViewModel: HomePageViewModel<HomePageIntent, HomePageViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(SearchProductViewModel::class)]
    fun provideSearchProductViewModel(setProductViewModel: SearchProductViewModel<SearchProductIntent, SearchProductViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(ProductDetailsViewModel::class)]
    fun provideProductDetailsViewModel(productDetailsViewModel: ProductDetailsViewModel<ProductDetailsIntent, ProductDetailsViewState>) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(SelectedCategoryViewModel::class)]
    fun provideSelectedCategoryViewModel(selectedCategoryViewModel: SelectedCategoryViewModel<SelectedCategoryIntent, SelectedCategoryViewState>) : ViewModel

}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)