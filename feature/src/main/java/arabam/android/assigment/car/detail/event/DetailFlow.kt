package arabam.android.assigment.car.detail.event

import com.arabam.assigment.domain.model.error.BaseError
import com.arabam.assigment.domain.model.ui.CarDetailUIModel

sealed class DetailFlow {
    class DataReceived(val car: CarDetailUIModel?) : DetailFlow()
    object Retry : DetailFlow()
    class Error(val error: BaseError?) : DetailFlow()
    object Idle : DetailFlow()
}
