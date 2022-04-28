package arabam.android.assigment.car.list.bottomsheet

import arabam.android.assigment.databinding.BottomSheetSortDirectionBinding
import com.arabam.assigment.domain.model.SortDirection
import com.myetisir.core.ui.bottomsheet.ArabamBaseBottomSheet

class SortDirectionBottomSheet(private val sortDirections: SortDirectionData? = null) :
    ArabamBaseBottomSheet<BottomSheetSortDirectionBinding>() {

    lateinit var sortDirectionData: SortDirectionData
    private var listener: ((SortDirectionData) -> Unit)? = null

    override fun isDialogCancellable(): Boolean = true

    override fun getViewBinding(): BottomSheetSortDirectionBinding {
        return BottomSheetSortDirectionBinding.inflate(layoutInflater)
    }

    override fun viewLoaded() {
        super.viewLoaded()
        sortDirectionData = sortDirections ?: SortDirectionData()
        binding.apply {

            txtAscending.setOnClickListener {
                listener?.invoke(SortDirectionData(SortDirection.ASCENDING))
            }

            txtDescending.setOnClickListener {
                listener?.invoke(SortDirectionData(SortDirection.DESCENDING))
            }
        }
    }

    fun setOnSubmitListener(listener: (SortDirectionData) -> Unit) {
        this.listener = listener
    }

    class SortDirectionData(@SortDirection val direction: Int? = null)
}