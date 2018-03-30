package anton.photomap.presentation.maps

import anton.photomap.entity.MapPoint
import anton.photomap.model.data.sharedPrefs.SharedPrefs
import anton.photomap.model.interactor.PointsInteractor
import anton.photomap.presentation.global.BasePresenter
import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@InjectViewState
class MapsPresenter @Inject constructor(
        private val pointsInteractor: PointsInteractor,
        private val prefs: SharedPrefs
) : BasePresenter<MapsView>() {

    lateinit var listMapPoint: List<MapPoint>
    lateinit var selectedMapPoint: MapPoint

    fun onResume() {
        pointsInteractor.getMapPoints()
                .toList()
                .subscribeBy(
                        onSuccess = ::onSuccessDownload,
                        onError = ::onError
                ).connect()
    }

    private fun onSuccessDownload(listMapPoints: List<MapPoint>) {
        viewState.showMapPoints(listMapPoints)
        listMapPoint = listMapPoints
    }

    private fun onError(t: Throwable) {
        viewState.showMessage(t.toString())
    }

    fun onClickMarker(id: Long) {
        listMapPoint.find { it.id == id }?.let {
            viewState.showDetailsOfPoint(it)
            selectedMapPoint = it
        }
    }

    fun onClickEditPoint() {
        viewState.startFormCreationActivity(selectedMapPoint.id)
    }

    // Блокировка идет со стороны ui, потому проблем для "as Long" быть не должно
    fun onClickDeletePoint() {
        pointsInteractor.deleteMapPointById(selectedMapPoint.id as Long)
                .subscribeBy(
                        onComplete = ::onDeletionPointCompleted,
                        onError = ::onError
                ).connect()
    }

    private fun onDeletionPointCompleted() {
        prefs.isDataChanged = true
        viewState.deleteMarkerFromMap()
    }

    fun onClickAddPoint() {
        viewState.startFormCreationActivity()
    }
}