package ru.gozerov.presentation.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentHomePageBinding
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.findNavigationProvider

class HomePageFragment : Fragment(){

    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {
            findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerGlobal)
            findNavigationProvider().getRouter().navigateTo(Screens.signIn())
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
    }

    override fun onPause() {
        super.onPause()
        findNavigationProvider().removeNavigator()
    }

}