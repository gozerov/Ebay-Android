package ru.gozerov.presentation.screens.home.home_page.goods_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import ru.gozerov.domain.models.Good
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.ItemProductCardBinding

class GoodsListAdapter(
    private val onGoodClickedListener: (Good) -> Unit
) : RecyclerView.Adapter<GoodsListAdapter.ViewHolder>(), View.OnClickListener {

    inner class ViewHolder(val binding: ItemProductCardBinding) : RecyclerView.ViewHolder(binding.root)

    var data: List<Good> = emptyList()
        set(value) {
            val diffCallback = GoodsListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductCardBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder.binding) {
            root.tag = item
            imgProduct.load(item.images?.firstOrNull() ?: "https://www.poesiedautore.it/authors/noava.jpg"){
                transformations(RoundedCornersTransformation(16f))
                scale(Scale.FIT)
            }
            txtProductName.text = item.name
            txtProductPrice.text = root.context.getString(R.string.price_is, item.price)
            if (item.rating != null) {
                txtRating.text = item.rating.toString()
                txtRating.visibility = View.VISIBLE
                txtRating.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_star_16, 0, 0, 0)
                txtReviewsCount.text = root.context.getString(R.string.reviews_are, item.reviews?.size)
            } else {
                txtRating.visibility = View.GONE
                txtReviewsCount.text = root.context.getString(R.string.no_reviews)
            }
        }
    }

    override fun onClick(v: View?) {
        val good = v?.tag as? Good
        good?.let(onGoodClickedListener)
    }

}