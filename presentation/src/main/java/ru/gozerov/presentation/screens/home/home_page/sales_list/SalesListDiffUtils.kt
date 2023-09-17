package ru.gozerov.presentation.screens.home.home_page.sales_list

import androidx.recyclerview.widget.DiffUtil
import ru.gozerov.domain.models.Sale

class SalesListDiffUtils(
    private val oldList: List<Sale>,
    private val newList: List<Sale>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}