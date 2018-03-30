package anton.photomap.entity

import com.google.gson.annotations.SerializedName

class MapPoint(
        var id: Long? = null,
        @SerializedName("latitude") var latitude: Double,
        @SerializedName("longtitude") var longtitude: Double,
        @SerializedName("text") var text: String,
        @SerializedName("image") var image: String,
        @SerializedName("lastVisited") var lastVisited: String
)