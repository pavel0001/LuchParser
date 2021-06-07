package by.valtorn.luchparser.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerAdapter(
    var dataSet: List<BaseListItem> = listOf(),
    private val onItemClickListener: ((item: BaseListItem, position: Int) -> Unit)? = null
) : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.renderItem(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BaseViewHolder(view, onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int = dataSet[position].getViewId()

    fun updateWithDiffUtils(newItems: List<BaseListItem>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffCallback(dataSet, newItems))
        dataSet = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    open class BaseViewHolder(
        val view: View,
        private val onItemClickListener: ((item: BaseListItem, position: Int) -> Unit)?
    ) : RecyclerView.ViewHolder(view) {

        open fun renderItem(holderItem: BaseListItem) {
            holderItem.renderView(view, adapterPosition)
            view.setOnClickListener {
                onItemClickListener?.invoke(holderItem, adapterPosition)
            }
        }
    }
}

class BaseDiffCallback(private val oldItems: List<BaseListItem>, private val newItems: List<BaseListItem>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return oldItems[p0].isItemEquals(newItems[p1])
    }

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        return oldItems[p0].isItemEquals(newItems[p1])
    }

}