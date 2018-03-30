package anton.photomap.presentation.maps

import anton.photomap.entity.MapPoint
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MapsView : MvpView {
    fun showMapPoints(listMapPoints: List<MapPoint>)
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: String)

    fun showDetailsOfPoint(mapPoint: MapPoint)

    fun hideDetailsOfPoint()

    @StateStrategyType(SkipStrategy::class)
    fun startFormCreationActivity(id: Long? = null)

    fun deleteMarkerFromMap()
}