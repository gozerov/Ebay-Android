package ru.gozerov.presentation.screens.home.home_page.goods_list

import androidx.recyclerview.widget.DiffUtil
import ru.gozerov.domain.models.Good

class GoodsListDiffCallback(
    private val oldList: List<Good>,
    private val newList: List<Good>
) : DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].vendorCode == newList[newItemPosition].vendorCode
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}