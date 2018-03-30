package anton.photomap.model.data.db

import android.os.Parcelable
import io.requery.*

@Entity
interface MapPointDb : Parcelable, Persistable {
    @get:Key
    @get:Generated
    var id: Long

    var latitude: Double
    var longitude: Double
    var text: String
    var image: String
    var lastVisited: String
}