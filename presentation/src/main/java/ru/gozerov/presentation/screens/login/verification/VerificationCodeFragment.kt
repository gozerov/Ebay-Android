package ru.gozerov.presentation.screens.login.verification

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentVerificationCodeBinding
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION.NEW_PASSWORD
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION.SET_ACCOUNT_DATA
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.appComponent
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.hideKeyboard
import ru.gozerov.presentation.utils.showShortSnackbar

class VerificationCodeFragment : BaseFragment<VerificationCodeViewModel<VerificationCodeIntent, VerificationCodeViewState>>() {

    private lateinit var binding: FragmentVerificationCodeBinding
    override val viewModel: VerificationCodeViewModel<VerificationCodeIntent, VerificationCodeViewState> by viewModels { factory }

    override var toolbarType: ToolbarHolder.ToolbarType = ToolbarHolder.ToolbarType.NONE
    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerificationCodeBinding.inflate(inflater, container, false)
        viewModel.handleIntent(VerificationCodeIntent.StartScreen)

        if (binding.verificationCodeView.text.isEmpty())
            binding.continueButton.isEnabled = false

        binding.verificationCodeView.onTextChangedListener = { text ->
            binding.continueButton.isEnabled = (text.length == 4 && !viewModel.isConfirmInProgress)
        }

        binding.continueButton.setOnClickListener {
            setVerificationAvailability(isEnabled = false)
            hideKeyboard()
            viewModel.handleIntent(VerificationCodeIntent.ConfirmVerificationCode(binding.verificationCodeView.text.toInt()))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is VerificationCodeViewState.Empty -> {}
                        is VerificationCodeViewState.Success -> {
                            when(arguments?.get(ARG_DESTINATION) as NAV_DESTINATION) {
                                SET_ACCOUNT_DATA -> findNavigationProvider().getRouter().navigateTo(Screens.setAccountData())
                                NEW_PASSWORD -> findNavigationProvider().getRouter().navigateTo(Screens.enterNewPassword())
                            }
                        }
                        is VerificationCodeViewState.NotValidCode -> {
                            setVerificationAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.code_is_not_valid))
                        }
                        is VerificationCodeViewState.UnknownError -> {
                            setVerificationAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.unknown_error))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onPause() {
        findNavigationProvider().removeNavigator()
        super.onPause()
    }

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerGlobal)
    }

    private fun setVerificationAvailability(isEnabled: Boolean) {
        binding.continueButton.isEnabled = isEnabled
        binding.verificationCodeView.setIsEditTextEnabled(isEnabled)
    }

    companion object {

        private const val ARG_DESTINATION = "arg_destination"

        fun newInstance(navDestination: NAV_DESTINATION): VerificationCodeFragment {
            val fragment = VerificationCodeFragment()
            fragment.arguments = bundleOf(ARG_DESTINATION to navDestination)
            return fragment
        }

        enum class NAV_DESTINATION {
            SET_ACCOUNT_DATA, NEW_PASSWORD
        }

    }

}