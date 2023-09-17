package ru.gozerov.presentation.screens.home.home_page.goods_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.gozerov.domain.models.Category
import ru.gozerov.presentation.databinding.ItemCategoryCardBinding
import ru.gozerov.presentation.utils.bindGoodsCategory

class CategoryAdapter(
    private val onCategoryClickedListener: (category: Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(), View.OnClickListener {

    inner class ViewHolder(val binding: ItemCategoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    var data: List<Category> = emptyList()
        set(value) {
            val diffCallback = CategoryDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryCardBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder.binding) {
            root.tag = item
            txtCategoryName.text = item.name
            imgCategory.bindGoodsCategory(item.name)
        }
    }

    override fun onClick(v: View?) {
        val category = v?.tag as? Category
        category?.let(onCategoryClickedListener)
    }

}