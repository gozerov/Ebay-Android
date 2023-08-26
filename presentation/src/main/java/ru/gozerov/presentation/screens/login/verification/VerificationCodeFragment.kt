package ru.gozerov.presentation.screens.login.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.databinding.FragmentVerificationCodeBinding

class VerificationCodeFragment : Fragment() {

    private lateinit var binding: FragmentVerificationCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerificationCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

}