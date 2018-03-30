package anton.photomap.entity

import com.google.gson.annotations.SerializedName

class Places(@SerializedName("places") var places: List<MapPoint>)