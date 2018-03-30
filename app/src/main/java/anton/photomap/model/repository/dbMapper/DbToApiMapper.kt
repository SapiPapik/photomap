package anton.photomap.model.repository.dbMapper

import anton.photomap.entity.MapPoint
import anton.photomap.model.data.db.MapPointDb

fun MapPointDb.toPointApi() = MapPoint(
        id = this.id,
        latitude = this.latitude,
        longtitude = this.longitude,
        text = this.text,
        image = this.image,
        lastVisited = this.lastVisited
)
