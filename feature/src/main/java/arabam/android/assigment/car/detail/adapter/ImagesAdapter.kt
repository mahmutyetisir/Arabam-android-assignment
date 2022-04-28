package arabam.android.assigment.car.detail.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import arabam.android.assigment.car.detail.adapter.ImagesAdapter.ImagesViewHolder
import arabam.android.assigment.databinding.ItemImageBinding
import com.arabam.assigment.domain.helper.ImageSize
import com.arabam.assigment.domain.helper.updateImageUrlBySize
import com.bumptech.glide.Glide

class ImagesAdapter(private val clickListener: (String) -> Unit) :
    ListAdapter<String, ImagesViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ImagesViewHolder(
        private val binding: ItemImageBinding,
        private val clickListener: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(binding.root).load(imageUrl.updateImageUrlBySize(ImageSize.MediumTwo))
                .into(binding.ivImage)
            binding.ivImage.setOnClickListener {
                clickListener.invoke(imageUrl)
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}