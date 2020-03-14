package summer.example.ui

import androidx.recyclerview.widget.DiffUtil

class EqualsDiffCallback<TItem>(
    private val isSameItems: (TItem, TItem) -> Boolean
) : DiffUtil.ItemCallback<TItem>() {
    override fun areItemsTheSame(p0: TItem, p1: TItem) = isSameItems(p0, p1)
    override fun areContentsTheSame(p0: TItem, p1: TItem) = p0 == p1
}