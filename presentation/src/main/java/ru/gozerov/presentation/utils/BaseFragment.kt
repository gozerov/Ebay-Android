package ru.gozerov.presentation.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.utils.ToolbarHolder.ToolbarType.NAV_UP_ONLY
import javax.inject.Inject

abstract class BaseFragment<VM: BaseViewModel<out Intent, out ViewState>>: Fragment() {

    /**
     * inject this factory in onAttach
     *
         override fun onAttach(context: Context) {
             context.appComponent.inject(this)
             super.onAttach(context)
         }
     *
     */
    @Inject
    lateinit var factory: MultiViewModelFactory

    protected abstract val viewModel: VM

    protected open var toolbarType: ToolbarHolder.ToolbarType = NAV_UP_ONLY

    protected open var toolbarLabel: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as ToolbarHolder).onToolbarChange(toolbarType, toolbarLabel)
    }

    override fun onResume() {
        setNavigator()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        findNavigationProvider().removeNavigator()
    }

    /**
     * implement setNavigator like that
     *
        override fun setNavigator() {
            findNavigationProvider().setNavigator(requireActivity(), containerId)
        }
     *
     */
    abstract fun setNavigator()

}