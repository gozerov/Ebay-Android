package ru.gozerov.presentation.screens.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentTabsBinding
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider

class TabsFragment : Fragment() {

    private lateinit var binding: FragmentTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(inflater, container, false)

        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
        findNavigationProvider().getRouter().newRootScreen(Screens.homePage())

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        findNavigationProvider().removeNavigator()
    }

    companion object {

        private const val ARG_TOKEN = "ARG_TOKEN"

        fun newInstance(token: String?): TabsFragment {
            val fragment = TabsFragment()
            token?.let {
                fragment.arguments = bundleOf(ARG_TOKEN to it)
            }
            return fragment
        }

    }

}