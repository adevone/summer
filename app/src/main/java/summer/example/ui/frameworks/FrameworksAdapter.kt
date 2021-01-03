package summer.example.ui.frameworks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import summer.example.databinding.FrameworksItemBinding
import summer.example.entity.Basket
import summer.example.presentation.FrameworksInput
import summer.example.presentation.FrameworksViewModel
import summer.example.presentation.base.Hidden
import summer.example.ui.EqualsDiffCallback

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
                viewModel.pass(FrameworksInput.DecreaseClicked(item))
            }
            binding.countView.text = item.quantity.toString()
            binding.increaseButton.setOnClickListener {
                viewModel.pass(FrameworksInput.IncreaseClicked(item))
            }

            binding.root.setOnClickListener {
                viewModel.pass(FrameworksInput.ItemClicked(password = Hidden("123"), item))
            }
        }
    }
}