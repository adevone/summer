package io.adev.summer.example.ui.frameworks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.adev.summer.example.databinding.FrameworksItemBinding
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.presentation.FrameworksViewModel
import io.adev.summer.example.ui.EqualsDiffCallback

class FrameworksAdapter(
    private val viewModel: FrameworksViewModel,
) : ListAdapter<Basket.Item, FrameworksAdapter.ViewHolder>(
    EqualsDiffCallback { a, b -> a.framework.name == b.framework.name }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FrameworksItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val framework = getItem(position)
        holder.bind(framework)
    }

    inner class ViewHolder(
        private val binding: FrameworksItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Basket.Item) {

            binding.nameView.text = item.framework.name
            binding.versionView.text = item.framework.version

            binding.decreaseButton.setOnClickListener {
                viewModel.onDecreaseClick(item)
            }
            binding.countView.text = item.quantity.toString()
            binding.increaseButton.setOnClickListener {
                viewModel.onIncreaseClick(item)
            }

            binding.root.setOnClickListener {
                viewModel.onItemClick(item)
            }
        }
    }
}