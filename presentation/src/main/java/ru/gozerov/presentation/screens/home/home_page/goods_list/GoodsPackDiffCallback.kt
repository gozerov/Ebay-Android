package ru.gozerov.presentation.screens.home.home_page.goods_list

import androidx.recyclerview.widget.DiffUtil
import ru.gozerov.domain.models.Good

class GoodsPackDiffCallback(
    private val oldList: List<Pair<String, List<Good>>>,
    private val newList: List<Pair<String, List<Good>>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].first == newList[newItemPosition].first
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].first == newList[newItemPosition].first &&
                oldList[oldItemPosition].second == newList[newItemPosition].second
    }

}