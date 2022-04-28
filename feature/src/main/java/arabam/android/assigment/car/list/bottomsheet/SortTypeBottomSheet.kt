package arabam.android.assigment.car.list.bottomsheet

import arabam.android.assigment.databinding.BottomSheetSortTypeBinding
import com.arabam.assigment.domain.model.SortType
import com.myetisir.core.ui.bottomsheet.ArabamBaseBottomSheet

class SortTypeBottomSheet(private val sortTypes: SortTypeData? = null): ArabamBaseBottomSheet<BottomSheetSortTypeBinding>() {

    lateinit var sortTypeData: SortTypeData
    private var listener: ((SortTypeData) -> Unit)? = null

    override fun isDialogCancellable(): Boolean = true

    override fun getViewBinding(): BottomSheetSortTypeBinding {
        return BottomSheetSortTypeBinding.inflate(layoutInflater)
    }

    override fun viewLoaded() {
        super.viewLoaded()
        sortTypeData = sortTypes?: SortTypeData()
        binding.apply {

            txtPrice.setOnClickListener {
                listener?.invoke(SortTypeData(SortType.PRICE))
            }

            txtDate.setOnClickListener {
                listener?.invoke(SortTypeData(SortType.DATE))
            }

            txtYear.setOnClickListener {
                listener?.invoke(SortTypeData(SortType.YEAR))
            }
        }
    }

    fun setOnSubmitListener(listener: (SortTypeData) -> Unit) {
        this.listener = listener
    }

    class SortTypeData(@SortType val type: Int? = null)
}