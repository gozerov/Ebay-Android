package ru.gozerov.presentation.screens.login.enter_new_password

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
import ru.gozerov.presentation.databinding.FragmentEnterNewPasswordBinding
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.appComponent
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.hideKeyboard
import ru.gozerov.presentation.utils.showShortSnackbar

class EnterNewPasswordFragment : BaseFragment<EnterNewPasswordViewModel<EnterNewPasswordIntent, EnterNewPasswordViewState>>() {

    private lateinit var binding: FragmentEnterNewPasswordBinding

    override val viewModel: EnterNewPasswordViewModel<EnterNewPasswordIntent, EnterNewPasswordViewState> by viewModels { factory }

    override var actions: Map<ToolbarHolder.ActionType, ToolbarAction?> = mapOf(ToolbarHolder.ActionType.NONE to null)

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterNewPasswordBinding.inflate(inflater, container, false)
        viewModel.handleIntent(EnterNewPasswordIntent.StartScreen)

        if (binding.newPasswordEditText.text?.isEmpty() == true || binding.confirmPasswordEditText.text?.isEmpty() == true)
            binding.saveUpdateButton.isEnabled = false

        binding.newPasswordEditText.doOnTextChanged { text, _, _, _ ->
            binding.saveUpdateButton.isEnabled =
                (text?.isEmpty() == false && binding.confirmPasswordEditText.text?.isEmpty() == false) && !viewModel.isConfirmInProgress
        }

        binding.confirmPasswordEditText.doOnTextChanged { text, _, _, _ ->
            binding.saveUpdateButton.isEnabled =
                (text?.isEmpty() == false && binding.newPasswordEditText.text?.isEmpty() == false) && !viewModel.isConfirmInProgress
        }

        binding.saveUpdateButton.setOnClickListener {
            hideKeyboard()
            if (binding.newPasswordEditText.text.toString().length >= 6 && binding.confirmPasswordEditText.text.toString().length >= 6) {
                setEnteringAvailability(isEnabled = false)
                viewModel.handleIntent(
                    EnterNewPasswordIntent.SubmitPasswords(
                        password = binding.newPasswordEditText.text.toString(),
                        confirmedPassword = binding.confirmPasswordEditText.text.toString()
                    )
                )
            }
            else
                showShortSnackbar(getString(R.string.password_must_be_6))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is EnterNewPasswordViewState.Empty -> {}
                        is EnterNewPasswordViewState.Success -> {
                            findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
                            findNavigationProvider().getRouter().newRootScreen(Screens.homePage())
                        }
                        is EnterNewPasswordViewState.PasswordMismatch -> {
                            setEnteringAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.passwords_don_t_match))
                        }
                        is EnterNewPasswordViewState.UnknownError -> {
                            setEnteringAvailability(isEnabled = true)
                            showShortSnackbar(getString(R.string.unknown_error))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerGlobal)
    }

    private fun setEnteringAvailability(isEnabled: Boolean) {
        binding.saveUpdateButton.isEnabled = isEnabled
        binding.newPasswordEditText.isFocusableInTouchMode = isEnabled
        binding.newPasswordEditText.isFocusable = isEnabled
        binding.confirmPasswordEditText.isFocusableInTouchMode = isEnabled
        binding.confirmPasswordEditText.isFocusable = isEnabled
    }



}