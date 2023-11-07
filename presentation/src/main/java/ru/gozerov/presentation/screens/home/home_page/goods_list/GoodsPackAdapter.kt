package ru.gozerov.presentation.screens.home.home_page.goods_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gozerov.domain.models.Category
import ru.gozerov.domain.models.Good
import ru.gozerov.presentation.databinding.ItemProductsCollectionBinding
import ru.gozerov.presentation.utils.HorizontalMarginItemDecoration

class GoodsPackAdapter(
    private val onGoodClickedListener: (Good) -> Unit,
    private val onSeeAllClickedListener: (String) -> Unit,
    private val onMenuClicked: () -> Unit,

) : RecyclerView.Adapter<GoodsPackAdapter.ViewHolder>(), View.OnClickListener {

    inner class ViewHolder(val binding: ItemProductsCollectionBinding) : RecyclerView.ViewHolder(binding.root)

    var data: List<Pair<String, List<Good>>> = emptyList()
        set(value) {
            val diffCallback = GoodsPackDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductsCollectionBinding.inflate(inflater, parent, false)
        binding.txtSeeAllCollection.setOnClickListener(this)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        binding.productsRecyclerView.addItemDecoration(HorizontalMarginItemDecoration())
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val adapter = GoodsListAdapter(onGoodClickedListener, onMenuClicked)
        with(holder.binding) {
            txtSeeAllCollection.tag = item.first
            root.tag = item
            adapter.data = item.second
            txtCollectionName.text = item.first
            productsRecyclerView.adapter = adapter
        }
    }

    override fun onClick(v: View?) {
        val packType = v?.tag as? String
        packType?.let(onSeeAllClickedListener)
    }

}