package ru.gozerov.presentation.screens.login.sign_up.email

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
import ru.gozerov.presentation.databinding.FragmentRegisterAccountBinding
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION.SET_ACCOUNT_DATA
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.appComponent
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.hideKeyboard
import ru.gozerov.presentation.utils.showShortSnackbar

class RegisterAccountFragment : BaseFragment<RegisterAccountViewModel<RegisterAccountIntent, RegisterAccountViewState>>() {

    private lateinit var binding: FragmentRegisterAccountBinding

    override val viewModel: RegisterAccountViewModel<RegisterAccountIntent, RegisterAccountViewState> by viewModels { factory }

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
        binding = FragmentRegisterAccountBinding.inflate(inflater, container, false)
        viewModel.handleIntent(RegisterAccountIntent.StartScreen)

        if (binding.emailEditText.text?.isEmpty() == true)
            binding.continueButton.isEnabled = false

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            binding.continueButton.isEnabled = text?.isEmpty() == false && !viewModel.isRegisterInProgress
        }

        binding.continueButton.setOnClickListener {
            hideKeyboard()
            if (binding.emailEditText.text.toString().length >= 5) {
                setRegisterAvailability(false)
                viewModel.handleIntent(RegisterAccountIntent.PerformSignUp(binding.emailEditText.text.toString()))
            }
            else
                showShortSnackbar(getString(R.string.email_must_be_5))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is RegisterAccountViewState.Empty -> {}
                        is RegisterAccountViewState.Success -> {
                            findNavigationProvider().getRouter().replaceScreen(Screens.verificationCode(SET_ACCOUNT_DATA, binding.emailEditText.text.toString()))
                        }
                        is RegisterAccountViewState.AccountAlreadyExist -> {
                            setRegisterAvailability(true)
                            showShortSnackbar(getString(R.string.account_already_exist))
                        }
                        is RegisterAccountViewState.UnknownError -> {
                            setRegisterAvailability(true)
                            showShortSnackbar(getString(R.string.unknown_error))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun setRegisterAvailability(isEnabled: Boolean) {
        binding.continueButton.isEnabled = isEnabled
        binding.emailEditText.isFocusableInTouchMode = isEnabled
        binding.emailEditText.isFocusable = isEnabled
    }


}