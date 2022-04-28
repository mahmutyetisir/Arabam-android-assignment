package arabam.android.assigment.car.list.event

import com.arabam.assigment.domain.model.ui.CarItemUIModel

sealed class ListFragmentAction {
    class CarItemAction(val car: CarItemUIModel?): ListFragmentAction()
    object Retry : ListFragmentAction()
    object Filter: ListFragmentAction()
    object SortTypes: ListFragmentAction()
    object SortDirection: ListFragmentAction()
    object Idle: ListFragmentAction()
}
