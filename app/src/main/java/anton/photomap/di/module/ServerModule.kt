package anton.photomap.di.module

import anton.photomap.di.provider.ApiProvider
import anton.photomap.di.provider.GsonProvider
import anton.photomap.di.provider.OkHttpClientProvider
import anton.photomap.di.qualifier.ServerPath
import anton.photomap.model.data.server.InteresneeApi
import anton.photomap.model.interactor.PointsInteractor
import anton.photomap.model.repository.PointsApiRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import toothpick.config.Module

class ServerModule(serverUrl: String): Module() {
    init {
        bind(String::class.java).withName(ServerPath::class.java).toInstance(serverUrl)
        bind(Gson::class.java).toProvider(GsonProvider::class.java).providesSingletonInScope()
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).providesSingletonInScope()
        bind(InteresneeApi::class.java).toProvider(ApiProvider::class.java).providesSingletonInScope()

        //Points
        bind(PointsApiRepository::class.java).singletonInScope()
        bind(PointsInteractor::class.java).singletonInScope()
    }
}