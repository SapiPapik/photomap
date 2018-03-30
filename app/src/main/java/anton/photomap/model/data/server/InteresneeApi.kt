package anton.photomap.model.data.server

import anton.photomap.entity.Places
import io.reactivex.Single
import retrofit2.http.GET


interface InteresneeApi {
    @GET("android-middle-level-data.json")
    fun getPoints(): Single<Places>
}