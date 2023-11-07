package ru.gozerov.presentation.screens.login.verification

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentVerificationCodeBinding
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION.NEW_PASSWORD
import ru.gozerov.presentation.screens.login.verification.VerificationCodeFragment.Companion.NAV_DESTINATION.SET_ACCOUNT_DATA
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.hideKeyboard
import ru.gozerov.presentation.utils.showShortSnackbar


@AndroidEntryPoint
class VerificationCodeFragment : BaseFragment<VerificationCodeViewModel<VerificationCodeIntent, VerificationCodeViewState>>() {

    private lateinit var binding: FragmentVerificationCodeBinding

    override val viewModel: VerificationCodeViewModel<VerificationCodeIntent, VerificationCodeViewState> by viewModels { factory }

    override var actions: Map<ToolbarHolder.ActionType, ToolbarAction?> = mapOf(ToolbarHolder.ActionType.NONE to null)

    override fun onAttach(context: Context) {
        //context.appComponent.inject(this)
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

        binding.txtResendCode.setOnClickListener {
            val email = arguments?.getString(ARG_EMAIL).toString()
            viewModel.handleIntent(VerificationCodeIntent.ResendCode(email))
            setVerificationAvailability(isEnabled = false)
            binding.txtResetCode.visibility = View.VISIBLE
            binding.txtResetCodeTimer.visibility = View.VISIBLE
        }

        setChangeEmailSpan()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is VerificationCodeViewState.Empty -> {}
                        is VerificationCodeViewState.Success -> {
                            when(arguments?.get(ARG_DESTINATION) as NAV_DESTINATION) {
                                SET_ACCOUNT_DATA -> findNavigationProvider().getRouter().replaceScreen(Screens.setAccountData())
                                NEW_PASSWORD -> findNavigationProvider().getRouter().replaceScreen(Screens.enterNewPassword())
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
                        is VerificationCodeViewState.ResendUnavailable -> {
                            setVerificationAvailability(isEnabled = false)
                            showShortSnackbar(getString(R.string.verification_is_unavailable))
                        }
                        is VerificationCodeViewState.Timer -> {
                            binding.txtResetCodeTimer.text = getString(R.string.timer_sec, state.time)
                        }
                        is VerificationCodeViewState.TimerEnd -> {
                            setVerificationAvailability(isEnabled = true)
                            binding.txtResetCodeTimer.text = ""
                            binding.txtResetCode.visibility = View.GONE
                            binding.txtResetCodeTimer.visibility = View.GONE
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

    private fun setChangeEmailSpan() {
        val email = arguments?.getString(ARG_EMAIL)
        email?.run {
            val changeEmailClickable = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    findNavigationProvider().getRouter().exit()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = requireContext().getColor(R.color.blue_ocean)
                    ds.isUnderlineText = false
                }
            }

            val changeEmailText = getString(R.string.change)
            val text = getString(R.string.we_have_sent_code_to, this)
            val spannableString = SpannableString(text)
            spannableString.setSpan(
                changeEmailClickable,
                text.indexOf(changeEmailText),
                text.indexOf(changeEmailText) + changeEmailText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.txtChangeEmail.run {
                this.text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
                highlightColor = Color.TRANSPARENT
            }
        }
    }

    companion object {

        private const val ARG_DESTINATION = "arg_destination"
        private const val ARG_EMAIL = "arg_email"

        fun newInstance(navDestination: NAV_DESTINATION, email: String): VerificationCodeFragment {
            val fragment = VerificationCodeFragment()
            fragment.arguments = bundleOf(ARG_DESTINATION to navDestination, ARG_EMAIL to email)
            return fragment
        }

        enum class NAV_DESTINATION {
            SET_ACCOUNT_DATA, NEW_PASSWORD
        }

    }

}