package arabam.android.assigment.car.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import arabam.android.assigment.car.detail.event.DetailFlow
import com.arabam.assigment.domain.helper.ImageSize
import com.arabam.assigment.domain.helper.updateImageUrlBySize
import com.arabam.assigment.domain.model.Resource
import com.arabam.assigment.domain.usecase.GetCarDetailUseCase
import com.myetisir.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val GetCarDetailUseCase: GetCarDetailUseCase) :
    BaseViewModel() {
    private val _loadingEvent = MutableStateFlow<Boolean>(false)
    val loadingEvent: StateFlow<Boolean> get() = _loadingEvent

    private val _detailFlow = MutableStateFlow<DetailFlow>(DetailFlow.Idle)
    val detailFlow: StateFlow<DetailFlow> get() = _detailFlow

    fun getCarDetailData(id: Int) {
        viewModelScope.launch {
            GetCarDetailUseCase(id).collectLatest {
                when (it) {
                    is Resource.Error -> {
                        _loadingEvent.value = false
                        _detailFlow.value = DetailFlow.Error(it.exception)
                    }
                    Resource.Loading -> {
                        _loadingEvent.value = true
                    }
                    is Resource.Success -> {
                        _loadingEvent.value = false
                        it.data.photos = it.data.photos
                        _detailFlow.value = DetailFlow.DataReceived(it.data)
                    }
                }
            }
        }
    }
}