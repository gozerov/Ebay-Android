package ru.gozerov.presentation.screens.login.reset

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
import ru.gozerov.presentation.databinding.FragmentResetPasswordBinding
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION.NEW_PASSWORD
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.appComponent
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.hideKeyboard
import ru.gozerov.presentation.utils.showShortSnackbar

class ResetPasswordFragment : BaseFragment<ResetPasswordViewModel<ResetPasswordIntent, ResetPasswordViewState>>() {

    private lateinit var binding: FragmentResetPasswordBinding
    override val viewModel: ResetPasswordViewModel<ResetPasswordIntent, ResetPasswordViewState> by viewModels { factory }

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
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        viewModel.handleIntent(ResetPasswordIntent.StartScreen)
        binding.resetPasswordButton.setOnClickListener {
            hideKeyboard()
            if (binding.emailEditText.text.toString().length >= 5) {
                setEmailAvailability(isEnabled = false)
                viewModel.handleIntent(ResetPasswordIntent.ResetPasswordByEmail(binding.emailEditText.text.toString()))
            }
            else
                showShortSnackbar(getString(R.string.email_must_be_5))
        }

        if (binding.emailEditText.text?.isEmpty() == true)
            binding.resetPasswordButton.isEnabled = false

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            binding.resetPasswordButton.isEnabled = text?.isEmpty() == false
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is ResetPasswordViewState.Empty -> {}
                        is ResetPasswordViewState.Success -> {
                            findNavigationProvider().getRouter().navigateTo(Screens.verificationCode(NEW_PASSWORD))
                        }
                        is ResetPasswordViewState.InvalidEmail -> {
                            setEmailAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.email_isn_t_valid))
                        }
                        is ResetPasswordViewState.UnknownError -> {
                            setEmailAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.unknown_error))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun setEmailAvailability(isEnabled: Boolean) {
        binding.resetPasswordButton.isEnabled = isEnabled
        binding.emailEditText.isFocusable = isEnabled
        binding.emailEditText.isFocusableInTouchMode = isEnabled
    }


}