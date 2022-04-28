package arabam.android.assigment.car.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import arabam.android.assigment.R
import arabam.android.assigment.databinding.ItemLoadStateBinding
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

class CarLoadStateAdapter(
    private val lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val retry: () -> Unit
) : LoadStateAdapter<CarLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(lifecycleCoroutineScope, parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    class LoadStateViewHolder(
        private val lifecycleCoroutineScope: LifecycleCoroutineScope,
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
    ) {
        private val binding = ItemLoadStateBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.progressBar
        private val errorMsg: TextView = binding.errorMsg
        private val retry: Button = binding.retryButton.also {
            it.clicks().onEach {
                retry()
            }.debounce(CLICK_TIMEOUT).launchIn(lifecycleCoroutineScope)
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            const val CLICK_TIMEOUT = 300L
        }
    }
}