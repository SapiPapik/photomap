package anton.photomap.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import anton.photomap.R
import anton.photomap.di.DI
import anton.photomap.entity.MapPoint
import anton.photomap.presentation.main.MainPresenter
import anton.photomap.presentation.main.MainView
import anton.photomap.service.DownloadMapPointsService
import anton.photomap.ui.adapter.MapPointAdapter
import anton.photomap.ui.global.BaseActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import toothpick.Toothpick

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = Toothpick
            .openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE, DI.DB_SCOPE, DI.MAIN_SCOPE)
            .getInstance(MainPresenter::class.java)

    private lateinit var adapter: MapPointAdapter

    override val resId: Int = R.layout.activity_main

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun initContent() {
        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val result = intent.getBooleanExtra(DownloadMapPointsService.RESULT, false)
                if (result) {
                    presenter.onDownloadMapPoints()
                } else {
                    presenter.onFailDownloadMapPoints()
                }
            }
        }, IntentFilter(DownloadMapPointsService.NAME))

        rvListMapPoints.layoutManager = LinearLayoutManager(this)
        adapter = MapPointAdapter(ArrayList(), { presenter.onClickItemList(it) })
        rvListMapPoints.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(DI.MAIN_SCOPE)
    }

    override fun startLoadingService() {
        val intent = Intent(this, DownloadMapPointsService::class.java)
        this.startService(intent)
    }

    override fun showListPoints(listPoints: List<MapPoint>) {
        adapter.addNewList(listPoints)
    }

    override fun startMapsActivity(mapPoint: MapPoint) {
        startActivity(intentFor<MapsActivity>(MapsActivity.ID to mapPoint.id))
    }
}
