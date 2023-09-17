package ru.gozerov.presentation.screens.home.home_page.sales_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import ru.gozerov.domain.models.Sale
import ru.gozerov.presentation.databinding.ItemSaleCardBinding

class SalesListAdapter(
    private val onItemClickListener: (item: Sale) -> Unit
) : RecyclerView.Adapter<SalesListAdapter.ViewHolder>(), View.OnClickListener {

    inner class ViewHolder(val binding: ItemSaleCardBinding) : RecyclerView.ViewHolder(binding.root)

    var data: List<Sale> = emptyList()
        set(value) {
            val diffCallback = SalesListDiffUtils(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSaleCardBinding.inflate(layoutInflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder.binding) {
            root.tag = item
            root.load(item.image) {
                transformations(RoundedCornersTransformation(16f))
                scale(Scale.FIT)
            }
        }
    }

    override fun onClick(v: View?) {
        val item = v?.tag as? Sale
        item?.let(onItemClickListener)
    }

}