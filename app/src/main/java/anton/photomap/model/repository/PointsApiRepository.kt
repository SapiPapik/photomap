package anton.photomap.model.repository

import anton.photomap.model.data.server.InteresneeApi
import anton.photomap.model.system.SchedulersProvider
import javax.inject.Inject


class PointsApiRepository @Inject constructor(
        private val api: InteresneeApi,
        private val schedulers: SchedulersProvider
) {
    fun getAll() = api
            .getPoints()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
}