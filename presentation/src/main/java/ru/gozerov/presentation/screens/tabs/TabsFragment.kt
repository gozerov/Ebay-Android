package ru.gozerov.presentation.screens.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.databinding.FragmentTabsBinding
import ru.gozerov.presentation.utils.NavigatorType
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.findNavigationProvider

class TabsFragment : Fragment() {

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(inflater, container, false)

        findNavigationProvider().getRouter().newRootScreen(Screens.homePage())
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        findNavigationProvider().setNavigator(requireActivity(), NavigatorType.TABS)
    }

    override fun onPause() {
        super.onPause()
        findNavigationProvider().removeNavigator()
    }

}