package anton.photomap.model.interactor

import anton.photomap.entity.MapPoint
import anton.photomap.model.data.db.MapPointDb
import anton.photomap.model.repository.PointsApiRepository
import anton.photomap.model.repository.PointsDbRepository
import anton.photomap.model.repository.dbMapper.toMapPointDb
import anton.photomap.model.repository.dbMapper.toPointApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import javax.inject.Inject

class PointsInteractor @Inject constructor(
        private val pointsRepository: PointsApiRepository,
        private val pointsDbRepository: PointsDbRepository
) {
    fun getMapPoints(): Observable<MapPoint> = pointsDbRepository.getAll().map(MapPointDb::toPointApi)

    fun getMapPointsFirstTime(): Completable =
            pointsRepository.getAll().map { save(it.places) }.toCompletable()

    fun getMapPointById(id: Long): Single<MapPoint> =
            pointsDbRepository.getById(id).map(MapPointDb::toPointApi)

    private fun save(listMapPoints: List<MapPoint>) =
            listMapPoints.forEach { pointsDbRepository.add(it.toMapPointDb()) }

    fun deleteMapPointById(id: Long): Completable = pointsDbRepository.delete(id)

    fun saveMapPoint(mapPoint: MapPoint) =
            pointsDbRepository.add(mapPoint.toMapPointDb()).toSingle().toCompletable()

}