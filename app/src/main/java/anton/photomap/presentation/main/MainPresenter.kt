package anton.photomap.presentation.main

import anton.photomap.entity.MapPoint
import anton.photomap.model.data.sharedPrefs.SharedPrefs
import anton.photomap.model.interactor.PointsInteractor
import anton.photomap.presentation.global.BasePresenter
import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val pointsInteractor: PointsInteractor,
        private val prefs: SharedPrefs
) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (prefs.isFirstTimeLoading) {
            startLoadService()
        } else {
            getMapPoints()
        }
    }

    fun onResume() {
        if (prefs.isDataChanged) {
            prefs.isDataChanged = false
            getMapPoints()
        }
    }

    private fun startLoadService() {
        viewState.startLoadingService()
        viewState.showProgress(true)
    }

    fun onDownloadMapPoints() {
        viewState.showProgress(false)
        prefs.isFirstTimeLoading = false
        getMapPoints()
    }

    private fun getMapPoints() {
        pointsInteractor.getMapPoints()
                .toList()
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribeBy(
                        onSuccess = ::onGettingMapPoints,
                        onError = ::onError
                ).connect()
    }

    private fun onGettingMapPoints(listPoints: List<MapPoint>) {
        viewState.showListPoints(listPoints)
    }

    private fun onError(t: Throwable) {
        viewState.showMessage(t.toString())
    }

    fun onFailDownloadMapPoints() {
        viewState.showProgress(false)
        //TODO: refactor
        viewState.showMessage("error getting points from api")
    }

    fun onClickItemList(mapPoint: MapPoint) {
        viewState.startMapsActivity(mapPoint)
    }
}