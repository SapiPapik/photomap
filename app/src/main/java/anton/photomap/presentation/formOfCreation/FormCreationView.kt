package anton.photomap.presentation.formOfCreation

import anton.photomap.entity.MapPoint
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface FormCreationView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: String)

    fun showDataMapPoint(mapPoint: MapPoint)
    fun finishActivity()
    fun showDatePicker(mapPoint: MapPoint?)
}