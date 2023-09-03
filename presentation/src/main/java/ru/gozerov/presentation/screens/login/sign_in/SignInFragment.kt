package ru.gozerov.presentation.screens.login.sign_in

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentSignInBinding
import ru.gozerov.presentation.screens.home.HomePageFragment
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.appComponent
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.hideKeyboard
import ru.gozerov.presentation.utils.showShortSnackbar

class SignInFragment : BaseFragment<SignInViewModel<SignInIntent, SignInViewState>>() {

    private lateinit var binding: FragmentSignInBinding

    override val viewModel: SignInViewModel<SignInIntent, SignInViewState> by viewModels { factory }
    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerGlobal)
    }
    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.handleIntent(SignInIntent.StartScreen)
        }

        if (binding.emailEditText.text?.isEmpty() == true || binding.passwordEditText.text?.isEmpty() == true)
            binding.signInButton.isEnabled = false

        binding.passwordEditText.doOnTextChanged { text, _, _, _ ->
            binding.signInButton.isEnabled = (text?.isEmpty() == false && binding.emailEditText.text?.isEmpty() == false) && !viewModel.isLoginInProgress
        }

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            binding.signInButton.isEnabled = (text?.isEmpty() == false && binding.passwordEditText.text?.isEmpty() == false) && !viewModel.isLoginInProgress
        }

        binding.txtForgotPassword.setOnClickListener {
            findNavigationProvider().getRouter().navigateTo(Screens.forgotPassword())
        }

        binding.txtSignUp.setOnClickListener {
            findNavigationProvider().getRouter().navigateTo(Screens.registerAccount())
        }

        binding.signInButton.setOnClickListener {
            hideKeyboard()
            if (isLoginAndPasswordNotEmpty()) {
                val loginText = binding.emailEditText.editableText.toString()
                val passwordText = binding.passwordEditText.editableText.toString()
                if (loginText.length >= 3) {
                    if (passwordText.length >= 6) {
                        setLoginAvailability(isEnabled = false)
                        viewModel.handleIntent(SignInIntent.TryLogin(loginText, passwordText))
                    } else
                        showShortSnackbar(getString(R.string.password_must_be_6))
                } else
                    showShortSnackbar(getString(R.string.email_must_be_5))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is SignInViewState.Empty -> {}
                        is SignInViewState.UserNotFound -> {
                            setLoginAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.user_not_found))
                        }
                        is SignInViewState.WrongPassword -> {
                            setLoginAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.wrong_password))
                        }
                        is SignInViewState.UnknownError -> {
                            setLoginAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.unknown_error))
                        }
                        is SignInViewState.SuccessLogin -> {
                            findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
                            findNavigationProvider().getRouter().newRootScreen(Screens.homePage())
                        }
                    }
                }
            }
        }
        return binding.root
    }
    private fun setLoginAvailability(isEnabled: Boolean) {
        binding.signInButton.isEnabled = isEnabled
        binding.emailEditText.isFocusableInTouchMode = isEnabled
        binding.emailEditText.isFocusable = isEnabled
        binding.passwordEditText.isFocusableInTouchMode = isEnabled
        binding.passwordInputLayout.isFocusable = isEnabled
        binding.passwordInputLayout.isClickable = isEnabled
    }

    private fun isLoginAndPasswordNotEmpty() : Boolean {
        return binding.emailEditText.text?.isEmpty() == false && binding.passwordEditText.text?.isEmpty() == false
    }

}