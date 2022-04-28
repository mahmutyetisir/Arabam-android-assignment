package arabam.android.assigment.car.list.bottomsheet

import androidx.core.widget.addTextChangedListener
import arabam.android.assigment.databinding.BottomSheetFilterBinding
import com.myetisir.core.ui.bottomsheet.ArabamBaseBottomSheet

class FilterBottomSheet(private val filters: FilterData? = null): ArabamBaseBottomSheet<BottomSheetFilterBinding>() {

    lateinit var filterData: FilterData
    private var listener: ((FilterData) -> Unit)? = null

    override fun isDialogCancellable(): Boolean = true

    override fun getViewBinding(): BottomSheetFilterBinding {
        return BottomSheetFilterBinding.inflate(layoutInflater)
    }

    override fun viewLoaded() {
        super.viewLoaded()
        filterData = filters?:FilterData()
        binding.apply {
            categoryId.setText(filterData.categoryId?.toString())
            maxYear.setText(filterData.maxYear?.toString())
            minYear.setText(filterData.minYear?.toString())
            categoryId.addTextChangedListener {
                filterData.categoryId = if (it.isNullOrEmpty()) null else it.toString().toInt()
            }
            maxYear.addTextChangedListener {
                filterData.maxYear = if (it.isNullOrEmpty()) null else it.toString().toInt()
            }
            minYear.addTextChangedListener {
                filterData.minYear = if (it.isNullOrEmpty()) null else it.toString().toInt()
            }
            bntFilterSubmit.setOnClickListener {
                listener?.invoke(filterData)
            }
        }
    }

    fun setOnSubmitListener(listener: (FilterData) -> Unit) {
        this.listener = listener
    }

    class FilterData(var categoryId: Int?=null, var minYear: Int?=null, var maxYear: Int?=null)
}