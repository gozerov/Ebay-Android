package ru.gozerov.presentation.screens.login.sign_up.account_data

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentSetAccountDataBinding
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.hideKeyboard
import ru.gozerov.presentation.utils.showShortSnackbar

@AndroidEntryPoint
class SetAccountDataFragment : BaseFragment<SetAccountDataViewModel<SetAccountDataIntent, SetAccountDataViewState>>() {

    private lateinit var binding: FragmentSetAccountDataBinding

    override val viewModel: SetAccountDataViewModel<SetAccountDataIntent, SetAccountDataViewState> by viewModels { factory }

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
        binding = FragmentSetAccountDataBinding.inflate(inflater, container, false)
        viewModel.handleIntent(SetAccountDataIntent.StartScreen)

        if (binding.usernameEditText.text?.isEmpty() == true || binding.passwordEditText.text?.isEmpty() == true)
            binding.confirmButton.isEnabled = false

        binding.passwordEditText.doOnTextChanged { text, _, _, _ ->
            binding.confirmButton.isEnabled = (text?.isEmpty() == false && binding.usernameEditText.text?.isEmpty() == false) && !viewModel.isSettingInProgress
        }

        binding.usernameEditText.doOnTextChanged { text, _, _, _ ->
            binding.confirmButton.isEnabled = (text?.isEmpty() == false && binding.passwordEditText.text?.isEmpty() == false) && !viewModel.isSettingInProgress
        }

        binding.confirmButton.setOnClickListener {
            hideKeyboard()
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (username.length >= 3) {
                    if (password.length >= 6) {
                        setInterfaceAvailability(false)
                        viewModel.handleIntent(SetAccountDataIntent.SetAccountData(
                            username = username,
                            password = password
                        ))
                    } else
                        showShortSnackbar(getString(R.string.password_must_be_6))
                } else
                    showShortSnackbar(getString(R.string.username_must_be_3))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is SetAccountDataViewState.Empty -> {}
                        is SetAccountDataViewState.Success -> {
                            findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
                            findNavigationProvider().getRouter().newRootScreen(Screens.homePage())
                        }
                        is SetAccountDataViewState.UnknownError -> {
                            setInterfaceAvailability(true)
                            showShortSnackbar(getString(R.string.unknown_error))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
    }

    private fun setInterfaceAvailability(isEnabled: Boolean) {
        binding.confirmButton.isEnabled = isEnabled
        binding.usernameEditText.isFocusableInTouchMode = isEnabled
        binding.usernameEditText.isFocusable = isEnabled
        binding.passwordEditText.isFocusableInTouchMode = isEnabled
        binding.passwordInputLayout.isFocusable = isEnabled
        binding.passwordInputLayout.isClickable = isEnabled
    }


}