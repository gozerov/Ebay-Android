package ru.gozerov.presentation.screens.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.FragmentHomePageBinding
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.appComponent
import ru.gozerov.presentation.utils.findNavigationProvider

class HomePageFragment : BaseFragment<HomePageViewModel<HomePageIntent, HomePageViewState>>(){

    private lateinit var binding: FragmentHomePageBinding

    override val viewModel: HomePageViewModel<HomePageIntent, HomePageViewState> by viewModels { factory }

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
        binding = FragmentHomePageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerTabs)
    }

}