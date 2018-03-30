package anton.photomap.presentation.formOfCreation

import anton.photomap.entity.MapPoint
import anton.photomap.model.data.sharedPrefs.SharedPrefs
import anton.photomap.model.interactor.PointsInteractor
import anton.photomap.presentation.global.BasePresenter
import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


@InjectViewState
class FormCreationPresenter @Inject constructor(
        private val pointsInteractor: PointsInteractor,
        private val prefs: SharedPrefs
) : BasePresenter<FormCreationView>() {

    private var selectedMapPoint: MapPoint? = null

    fun editMapPoint(id: Long) {
        pointsInteractor.getMapPointById(id)
                .subscribeBy(
                        onSuccess = ::onSuccessGetting,
                        onError = ::onError
                ).connect()
    }

    private fun onSuccessGetting(mapPoint: MapPoint) {
        selectedMapPoint = mapPoint
        viewState.showDataMapPoint(mapPoint)
    }

    private fun onError(t: Throwable) {
        viewState.showMessage(t.toString())
    }

    fun onClickLastVisited() {
        viewState.showDatePicker(selectedMapPoint)
    }

    fun onButtonSave(mapPoint: MapPoint) {
        selectedMapPoint?.let { mapPoint.id = it.id }
        pointsInteractor.saveMapPoint(mapPoint)
                .subscribeBy(
                        onComplete = ::onCompletedSaving,
                        onError = ::onError
                ).connect()
    }

    private fun onCompletedSaving() {
        prefs.isDataChanged = true
        viewState.finishActivity()
    }
}