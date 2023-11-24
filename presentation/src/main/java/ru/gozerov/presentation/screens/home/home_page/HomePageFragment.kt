package ru.gozerov.presentation.screens.home.home_page

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.DialogProductPopUpBinding
import ru.gozerov.presentation.databinding.FragmentHomePageBinding
import ru.gozerov.presentation.screens.home.home_page.goods_list.CategoryAdapter
import ru.gozerov.presentation.screens.home.home_page.goods_list.GoodsPackAdapter
import ru.gozerov.presentation.screens.home.home_page.sales_list.SalesListAdapter
import ru.gozerov.presentation.utils.BaseFragment
import ru.gozerov.presentation.utils.HorizontalMarginItemDecoration
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.log
import ru.gozerov.presentation.utils.showShortSnackbar

@AndroidEntryPoint
class HomePageFragment : BaseFragment<HomePageViewModel<HomePageIntent, HomePageViewState>>(){

    private lateinit var binding: FragmentHomePageBinding

    override val viewModel: HomePageViewModel<HomePageIntent, HomePageViewState> by viewModels { factory }

    override var actions: Map<ToolbarHolder.ActionType, ToolbarAction?> = mapOf(
        ToolbarHolder.ActionType.NOTIFICATION to {

        },
        ToolbarHolder.ActionType.CART to {

        }
    )

    override var titleStyle: Int? = R.style.BlueTitleTextAppearance

    private val categoryAdapter = CategoryAdapter {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerGlobal)
        findNavigationProvider().getRouter().navigateTo(Screens.selectedCategory(it.name))
    }

    private val salesAdapter = SalesListAdapter {
        log(it.toString())
    }

    private val goodsPackAdapter = GoodsPackAdapter(
        onGoodClickedListener = {
            findNavigationProvider().getRouter().navigateTo(Screens.productDetails(it.vendorCode))
        },
        onSeeAllClickedListener = {
            log(it)
        },
        onMenuClicked = {

            val dialogBinding = DialogProductPopUpBinding.inflate(LayoutInflater.from(context))
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .setCancelable(true)
                .create()
            val width = requireContext().resources.getDimensionPixelOffset(R.dimen.dialog_width)
            dialogBinding.closeDialog.setOnClickListener {
                dialog.cancel()
            }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            dialog.window?.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
    )

    override fun onAttach(context: Context) {
        toolbarLabel = getString(R.string.ebay)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)

        binding.searchGoodEditText.setOnClickListener {
            findNavigationProvider().getRouter().navigateTo(Screens.searchProduct())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when(state) {
                        is HomePageViewState.Empty -> {}
                        is HomePageViewState.SuccessCategoriesLoading -> {
                            categoryAdapter.data = state.value
                        }
                        is HomePageViewState.SuccessGoodsLoading -> {
                            goodsPackAdapter.data = state.value.map { it.key to it.value }
                        }
                        is HomePageViewState.SuccessSalesLoading -> {
                            salesAdapter.data = state.value
                        }
                        is HomePageViewState.UnknownError -> {
                            showShortSnackbar(getString(R.string.unknown_error))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saleCardsRecyclerView.adapter = salesAdapter
        binding.saleCardsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.saleCardsRecyclerView.addItemDecoration(HorizontalMarginItemDecoration())

        binding.categoriesRecyclerView.adapter = categoryAdapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRecyclerView.addItemDecoration(HorizontalMarginItemDecoration(innerMarginDimenId = R.dimen.margin_16))

        binding.productsCollectionRecyclerView.adapter = goodsPackAdapter
        binding.productsCollectionRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setNavigator() {
        findNavigationProvider().setNavigator(requireActivity(), R.id.fragmentContainerGlobal)
    }

    companion object {

        private const val ARG_TOKEN = "ARG_TOKEN"

        fun newInstance(token: String? = null): HomePageFragment {
            val fragment = HomePageFragment()
            token?.let {
                fragment.arguments = bundleOf(ARG_TOKEN to it)
            }
            return fragment
        }

    }

}