package arabam.android.assigment.car.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import arabam.android.assigment.car.list.bottomsheet.FilterBottomSheet
import arabam.android.assigment.car.list.bottomsheet.SortDirectionBottomSheet
import arabam.android.assigment.car.list.bottomsheet.SortTypeBottomSheet
import arabam.android.assigment.car.list.event.ListFragmentAction
import com.arabam.assigment.domain.helper.ImageSize
import com.arabam.assigment.domain.helper.updateImageUrlBySize
import com.arabam.assigment.domain.model.ui.CarItemClickListener
import com.arabam.assigment.domain.model.ui.CarItemUIModel
import com.arabam.assigment.domain.usecase.GetCarListUseCase
import com.myetisir.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getCarListUseCase: GetCarListUseCase) :
    BaseViewModel() {

    private val _carsFlow = MutableLiveData<PagingData<CarItemUIModel>>()
    val carsFlow: LiveData<PagingData<CarItemUIModel>> get() = _carsFlow

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh: StateFlow<Boolean> get() = _isRefresh

    private val _listFragmentActions = MutableSharedFlow<ListFragmentAction>()
    val listFragmentActions: SharedFlow<ListFragmentAction> get() = _listFragmentActions

    var needInitRequestSent = true

    var filters: FilterBottomSheet.FilterData? = null
    var sortTypes: SortTypeBottomSheet.SortTypeData? = null
    var sortDirections: SortDirectionBottomSheet.SortDirectionData? = null

    private val itemClickListener: CarItemClickListener = object : CarItemClickListener {
        override fun invoke(currentCar: CarItemUIModel) {
            sendAction(ListFragmentAction.CarItemAction(currentCar))
        }
    }

    fun fetchCarList() {
        viewModelScope.launch {
            needInitRequestSent = false
            val queryParam = GetCarListUseCase.Params(
                take = 20,
                categoryId = filters?.categoryId,
                maxYear = filters?.maxYear,
                minYear = filters?.minYear,
                sort = sortTypes?.type,
                sortDirection = sortDirections?.direction
            )
            getCarListUseCase(queryParam).cachedIn(viewModelScope).collectLatest {
                val listenerList = it.map { currentCarItem ->
                    currentCarItem.clickListener = itemClickListener
                    currentCarItem.photo =
                        currentCarItem.photo?.updateImageUrlBySize(ImageSize.SmallOne)
                    currentCarItem
                }
                _carsFlow.value = listenerList
            }
        }
    }

    fun updateRefreshState(isRefresh: Boolean) {
        _isRefresh.value = isRefresh
    }

    fun retry() {
        sendAction(ListFragmentAction.Retry)
    }

    fun sendAction(action: ListFragmentAction) {
        viewModelScope.launch {
            _listFragmentActions.emit(action)
            _listFragmentActions.emit(ListFragmentAction.Idle)
        }
    }
}