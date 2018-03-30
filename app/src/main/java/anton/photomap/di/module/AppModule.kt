package anton.photomap.di.module

import android.content.Context
import anton.photomap.di.qualifier.DefaultServerPath
import anton.photomap.model.data.sharedPrefs.SharedPrefs
import anton.photomap.model.data.storage.Prefs
import anton.photomap.model.system.AppSchedulers
import anton.photomap.model.system.SchedulersProvider
import toothpick.config.Module

class AppModule(context: Context): Module() {
    init {
        //Global
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(DefaultServerPath::class.java).toInstance("http://interesnee.ru/files/")
        bind(SchedulersProvider::class.java).toInstance(AppSchedulers())

        bind(SharedPrefs::class.java).to(Prefs::class.java).singletonInScope()
    }
}