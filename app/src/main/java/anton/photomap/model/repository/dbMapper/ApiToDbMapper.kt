package anton.photomap.model.repository.dbMapper

import anton.photomap.entity.MapPoint
import anton.photomap.model.data.db.MapPointDbEntity

fun MapPoint.toMapPointDb() = MapPointDbEntity().also { mapPointDb ->
    id?.let { mapPointDb.id = it }
    mapPointDb.latitude = latitude
    mapPointDb.longitude = longtitude
    mapPointDb.text = text
    mapPointDb.image = image
    mapPointDb.lastVisited = lastVisited
}