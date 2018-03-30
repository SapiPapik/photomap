package anton.photomap.ui

import android.os.Bundle
import android.view.View
import anton.photomap.R
import anton.photomap.di.DI
import anton.photomap.entity.MapPoint
import anton.photomap.extensions.loadFromUrl
import anton.photomap.presentation.maps.MapsPresenter
import anton.photomap.presentation.maps.MapsView
import anton.photomap.ui.global.BaseActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.common.api.GoogleApiClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.intentFor
import toothpick.Toothpick

class MapsActivity :
        BaseActivity(),
        MapsView,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks {

    companion object {
        const val ID = "id"
    }

    private lateinit var googleMap: GoogleMap

    private var selectedMarker: Marker? = null

    @InjectPresenter
    lateinit var presenter: MapsPresenter

    @ProvidePresenter
    fun provideMapsPresenter(): MapsPresenter = Toothpick
            .openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE, DI.DB_SCOPE, DI.MAPS_SCOPE)
            .getInstance(MapsPresenter::class.java)

    override val resId: Int = R.layout.activity_maps

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun initContent() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btnEditMarker.setOnClickListener { presenter.onClickEditPoint() }
        btnDeleteMarker.setOnClickListener { presenter.onClickDeletePoint() }
        btnAddMarker.setOnClickListener { presenter.onClickAddPoint() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap.setOnMarkerClickListener(this)
    }

    override fun showMapPoints(listMapPoints: List<MapPoint>) {
        googleMap.clear()
        listMapPoints.forEach(::addMapPointOnMap)
        listMapPoints.find { it.id == intent.getLongExtra(ID, 0L) }?.let {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longtitude)))
        }
    }

    private fun addMapPointOnMap(mapPoint: MapPoint) {
        val mapPlace = LatLng(mapPoint.latitude, mapPoint.longtitude)
        googleMap.addMarker(
                MarkerOptions().position(mapPlace).title(mapPoint.text)
        ).tag = mapPoint.id
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(DI.MAPS_SCOPE)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.tag?.let {
            presenter.onClickMarker(it as Long)
            selectedMarker = marker
        }
        return true
    }

    override fun showDetailsOfPoint(mapPoint: MapPoint) {
        llDetailsOfPoints.visibility = View.VISIBLE
        with(mapPoint) {
            tvMarkerDescription.text = text
            tvMarkerLat.text = latitude.toString()
            tvMarkerLong.text = longtitude.toString()
            image.takeIf { it.isNotEmpty() }?.let {
                ivMarkerImage.loadFromUrl(it)
            }
        }
    }

    override fun hideDetailsOfPoint() {
        llDetailsOfPoints.visibility = View.GONE
    }

    override fun deleteMarkerFromMap() {
        selectedMarker?.remove()
        selectedMarker = null
        llDetailsOfPoints.visibility = View.GONE
    }

    override fun startFormCreationActivity(id: Long?) {
        startActivity(intentFor<FormCreationActivity>(FormCreationActivity.SELECTED_MAP_POINT to id))
    }

    override fun onConnected(p0: Bundle?) {}

    override fun onConnectionSuspended(p0: Int) {}
}
