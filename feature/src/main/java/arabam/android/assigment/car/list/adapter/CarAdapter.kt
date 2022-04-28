package arabam.android.assigment.car.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import arabam.android.assigment.databinding.ItemCarBinding
import com.arabam.assigment.domain.model.ui.CarItemUIModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks


class CarAdapter(private val lifecycleCoroutineScope: LifecycleCoroutineScope) :
    PagingDataAdapter<CarItemUIModel, CarAdapter.PeopleViewHolder>(
        CAR_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PeopleViewHolder(binding, lifecycleCoroutineScope)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PeopleViewHolder(
        private val binding: ItemCarBinding,
        private val lifecycleCoroutineScope: LifecycleCoroutineScope
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(carUIModel: CarItemUIModel?) {
            binding.root.clicks().onEach {
                carUIModel?.clickListener?.invoke(carUIModel)
            }.debounce(CLICK_TIMEOUT).launchIn(lifecycleCoroutineScope)

            Glide.with(binding.root)
                .load(carUIModel?.photo)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .into(binding.ivCar)
            binding.txtCarTitle.text = carUIModel?.title
            binding.txtCarPrice.text = carUIModel?.priceFormatted
            binding.txtCarLocation.text = carUIModel?.location?.toUIFormatted()
        }
    }

    companion object {

        private val CAR_COMPARATOR = object : DiffUtil.ItemCallback<CarItemUIModel>() {
            override fun areItemsTheSame(
                oldItem: CarItemUIModel,
                newItem: CarItemUIModel
            ): Boolean {
                return (oldItem == newItem)
            }

            override fun areContentsTheSame(
                oldItem: CarItemUIModel,
                newItem: CarItemUIModel
            ): Boolean =
                oldItem.id == newItem.id
        }

        private const val CLICK_TIMEOUT = 300L
    }
}