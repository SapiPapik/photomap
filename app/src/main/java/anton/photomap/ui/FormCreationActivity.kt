package anton.photomap.ui

import android.app.DatePickerDialog
import android.widget.TextView
import anton.photomap.R
import anton.photomap.di.DI
import anton.photomap.entity.MapPoint
import anton.photomap.extensions.loadFromUrl
import anton.photomap.extensions.toDate
import anton.photomap.presentation.formOfCreation.FormCreationPresenter
import anton.photomap.presentation.formOfCreation.FormCreationView
import anton.photomap.ui.global.BaseActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_form_of_creation.*
import toothpick.Toothpick
import java.util.*

class FormCreationActivity : BaseActivity(), FormCreationView {
    companion object {
        const val SELECTED_MAP_POINT = "selected_map_point"
    }

    override val resId: Int = R.layout.activity_form_of_creation

    @InjectPresenter
    lateinit var presenter: FormCreationPresenter

    @ProvidePresenter
    fun provideFormCreationPresenter(): FormCreationPresenter = Toothpick
            .openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE, DI.DB_SCOPE, DI.FORM_CREATION_SCOPE)
            .getInstance(FormCreationPresenter::class.java)

    override fun initContent() {
        intent.getLongExtra(SELECTED_MAP_POINT, 0L).takeIf { it != 0L }?.let {
            presenter.editMapPoint(it)
        }
        tvLastVisited.setOnClickListener { presenter.onClickLastVisited() }
        btnSave.setOnClickListener {
            val listTextViews = listOf<TextView>(
                    etDescriptionPoint,
                    etLatPoint,
                    etLongPoint,
                    tvLastVisited
            )
            if (validation(listTextViews)) {
                presenter.onButtonSave(
                        MapPoint(
                                id = null,
                                latitude = etLatPoint.text.toString().toDouble(),
                                longtitude = etLongPoint.text.toString().toDouble(),
                                text = etDescriptionPoint.text.toString(),
                                image = "",
                                lastVisited = tvLastVisited.text.toString()
                        ))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(DI.FORM_CREATION_SCOPE)
    }

    override fun showDataMapPoint(mapPoint: MapPoint) {
        etDescriptionPoint.setText(mapPoint.text)
        etLatPoint.setText(mapPoint.latitude.toString())
        etLongPoint.setText(mapPoint.longtitude.toString())
        ivImagePoint.loadFromUrl(mapPoint.image)
        tvLastVisited.text = mapPoint.lastVisited
    }

    override fun finishActivity() {
        finish()
    }

    override fun showDatePicker(mapPoint: MapPoint?) {
        val dateSetterCallBack = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            tvLastVisited.text = Date(year - 1900, month, day).toString()
        }

        val date = mapPoint?.lastVisited?.toDate() ?: Date()
        DatePickerDialog(
                this,
                dateSetterCallBack,
                date.year + 1900,
                date.month,
                date.day
        ).show()
    }

    private fun validation(listTextViews: List<TextView>): Boolean {
        for (item in listTextViews) {
            if (item.text.isEmpty()) run {
                item.error = resources.getString(R.string.obligation_field)
                return false
            } else {
                item.error = null
            }
        }
        return true
    }
}
