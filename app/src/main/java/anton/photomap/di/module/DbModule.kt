package anton.photomap.di.module

import anton.photomap.di.provider.DbProvider
import io.requery.reactivex.KotlinReactiveEntityStore
import toothpick.config.Module


class DbModule : Module() {
    init {
        bind(KotlinReactiveEntityStore::class.java).toProvider(DbProvider::class.java).providesSingletonInScope()
    }
}