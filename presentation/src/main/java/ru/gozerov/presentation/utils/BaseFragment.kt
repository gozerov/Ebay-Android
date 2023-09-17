package ru.gozerov.presentation.utils

import android.os.Bundle
import android.view.View
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.utils.ToolbarHolder.ActionType.NAV_UP
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

    protected open var actions: Map<ToolbarHolder.ActionType, ToolbarAction?> = mapOf(NAV_UP to null)
    protected open var toolbarLabel: String = ""
    @StyleRes protected open var titleStyle: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as ToolbarHolder).onToolbarChange(actions, toolbarLabel, titleStyle)
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