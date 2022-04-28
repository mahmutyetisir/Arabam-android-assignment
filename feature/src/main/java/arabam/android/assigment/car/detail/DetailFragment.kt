package arabam.android.assigment.car.detail

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import arabam.android.assigment.R
import arabam.android.assigment.car.detail.adapter.ImagesAdapter
import arabam.android.assigment.car.detail.adapter.LinePagerIndicatorDecoration
import arabam.android.assigment.car.detail.event.DetailFlow
import arabam.android.assigment.common.addRepeatingJob
import arabam.android.assigment.databinding.FragmentDetailBinding
import com.arabam.assigment.domain.helper.ImageSize
import com.arabam.assigment.domain.helper.updateImageUrlBySize
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import com.myetisir.core.common.invisible
import com.myetisir.core.common.viewBinding
import com.myetisir.core.ui.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModels()
    override val binding by viewBinding(FragmentDetailBinding::bind)

    private val adapter = ImagesAdapter { imageUrl ->
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_image)
        dialog.show()
        val zoomView: ImageView = dialog.findViewById(R.id.iv_big_image)
        val progressBar: ProgressBar = dialog.findViewById(R.id.progress_bar)
        val toolbar: Toolbar = dialog.findViewById(R.id.toolbar_big_image)
        toolbar.setNavigationOnClickListener {
            dialog.dismiss()
        }
        Glide.with(zoomView).load(imageUrl.updateImageUrlBySize(ImageSize.BigOne))
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    zoomView.setImageDrawable(resource)
                    progressBar.invisible()
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) = Unit
            })
    }

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        arguments?.getString("car_id")?.toInt()?.let { id ->
            viewModel.getCarDetailData(id)
        }
        binding.apply {
            LinearSnapHelper().attachToRecyclerView(rwImages)
            rwImages.addItemDecoration(LinePagerIndicatorDecoration())
            rwImages.adapter = adapter
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.loadingEvent.collect {
                binding.progressBar.isVisible = it
            }
        }

        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.detailFlow.collect {
                when (it) {
                    is DetailFlow.DataReceived -> {
                        it.car?.let { car ->
                            binding.apply {
                                toolbar.title = car.userInfo?.nameSurname
                                txtTitle.text = car.title
                                txtModelName.text = car.modelName
                                txtPrice.text = car.priceFormatted
                                txtDate.text = car.dateFormatted
                                txtLocation.text = car.location?.toUIFormatted()
                                txtName.text = car.userInfo?.nameSurname
                                txtPhone.text = car.userInfo?.phoneFormatted

                                layoutPhoneNumber.clicks().onEach {
                                    startDialActivity(car.userInfo?.phone)
                                }.debounce(CLICK_TIMEOUT).launchIn(lifecycleScope)

                                layoutProperties.removeAllViews()
                                car.properties?.map { property ->
                                    "${property.name}: ${property.value}"
                                }?.forEach { propertyText ->
                                    val propertyView = Chip(requireContext())
                                    propertyView.text = propertyText
                                    layoutProperties.addView(propertyView)
                                }

                                txtDescription.text = HtmlCompat.fromHtml(
                                    car.text ?: "",
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                            }
                            adapter.submitList(car.photos)
                        }
                    }
                    DetailFlow.Retry -> {
                    }
                    is DetailFlow.Error -> {
                        AlertDialog.Builder(requireActivity())
                            .setTitle(getString(R.string.alert_dialog_title))
                            .setMessage(
                                getString(
                                    R.string.alert_dialog_detail_message,
                                    it.error?.message
                                )
                            )
                            .setPositiveButton(getString(R.string.alert_dialog_ok_button)) { v, d ->

                            }.show()
                    }
                }
            }
        }
    }

    private fun startDialActivity(phoneNumber: String?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:+${phoneNumber}")
        startActivity(intent)
    }
}