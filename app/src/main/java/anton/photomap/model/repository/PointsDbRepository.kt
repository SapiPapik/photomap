package anton.photomap.model.repository

import android.util.Log
import anton.photomap.model.data.db.MapPointDb
import anton.photomap.model.system.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toCompletable
import io.requery.Persistable
import io.requery.kotlin.eq
import io.requery.reactivex.KotlinReactiveEntityStore
import javax.inject.Inject

class PointsDbRepository @Inject constructor(
        private val db: KotlinReactiveEntityStore<Persistable>,
        private val schedulers: SchedulersProvider
) {
    private val TAG = "[PDR]"

    fun getAll(): Observable<MapPointDb> = ((db.select(MapPointDb::class)).get().observable()
            ?: io.reactivex.Observable.empty())
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun add(mapPointDb: MapPointDb) {
        db.upsert(mapPointDb)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .doOnSubscribe { Log.d(TAG, "Start inserting $mapPointDb") }
                .subscribeBy(
                        onSuccess = { item ->
                            Log.d(TAG, "Inserted $item")
                        },
                        onError = { e ->
                            Log.d(TAG, "Error happened while inserting $mapPointDb")
                            e.printStackTrace()
                        }
                )
    }

    fun getById(id: Long): Single<MapPointDb> =
            (db.select(MapPointDb::class) where (MapPointDb::id eq id))
                    .get()
                    .observable()
                    .singleOrError()
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())

    fun delete(id: Long): Completable =
            (db.delete(MapPointDb::class) where (MapPointDb::id eq id))
                    .get()
                    .toCompletable()
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
}