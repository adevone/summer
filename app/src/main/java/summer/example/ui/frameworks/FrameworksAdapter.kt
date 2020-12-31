package summer.example.ui.frameworks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.frameworks_item.*
import summer.example.R
import summer.example.entity.Basket
import summer.example.presentation.FrameworksViewModel
import summer.example.presentation.base.Hidden
import summer.example.ui.EqualsDiffCallback

class FrameworksAdapter(
    private val viewModel: FrameworksViewModel
) : ListAdapter<Basket.Item, FrameworksAdapter.ViewHolder>(
    EqualsDiffCallback { a, b -> a.framework.name == b.framework.name }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.frameworks_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val framework = getItem(position)
        holder.bind(framework)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Basket.Item) {

            nameView.text = item.framework.name
            versionView.text = item.framework.version

            decreaseButton.setOnClickListener {
                viewModel.onDecreaseClick(item.framework)
            }
            countView.text = item.quantity.toString()
            increaseButton.setOnClickListener {
                viewModel.onIncreaseClick(item.framework)
            }

            containerView.setOnClickListener {
                viewModel.onFrameworkClick(Hidden("123"), item.framework)
            }
        }
    }
}