package anton.photomap.presentation.main

import anton.photomap.entity.MapPoint
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun startLoadingService()

    fun showProgress(show: Boolean)
    fun showListPoints(listPoints: List<MapPoint>)

    @StateStrategyType(SkipStrategy::class)
    fun startMapsActivity(mapPoint: MapPoint)
}