package anton.photomap.service

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import anton.photomap.di.DI
import anton.photomap.model.interactor.PointsInteractor
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Toothpick
import javax.inject.Inject

class DownloadMapPointsService : IntentService(NAME) {
    companion object {
        const val NAME = "DownloadMapPointsService"
        const val RESULT = "result"
    }

    @Inject
    lateinit var pointsInteractor: PointsInteractor

    override fun onCreate() {
        super.onCreate()
        Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE, DI.DB_SCOPE, DI.DOWNLOAD_SERVICE).apply {
            Toothpick.inject(this@DownloadMapPointsService, this)
        }
    }

    override fun onHandleIntent(p0: Intent?) {
        pointsInteractor.getMapPointsFirstTime()
                .subscribeBy(
                        onComplete = ::onComplete,
                        onError = ::onError
                )
    }

    private fun onComplete() {
        LocalBroadcastManager
                .getInstance(baseContext)
                .sendBroadcast(Intent(NAME).putExtra(RESULT, true))
    }

    private fun onError(t: Throwable) {
        LocalBroadcastManager
                .getInstance(baseContext)
                .sendBroadcast(Intent(NAME).putExtra(RESULT, false))
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(DI.DOWNLOAD_SERVICE)
    }
}
