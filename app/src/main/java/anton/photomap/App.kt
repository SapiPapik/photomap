package anton.photomap

import android.app.Application
import anton.photomap.di.DI
import anton.photomap.di.module.AppModule
import anton.photomap.di.module.DbModule
import anton.photomap.di.module.ServerModule
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator

class App : Application() {

    private val SERVER_PATH = "http://interesnee.ru/files/"

    override fun onCreate() {
        super.onCreate()

        initToothpick()
        initAppScope()
    }

    private fun initToothpick() {
        //Debug mode
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
            FactoryRegistryLocator.setRootRegistry(anton.photomap.FactoryRegistry())
            MemberInjectorRegistryLocator.setRootRegistry(anton.photomap.MemberInjectorRegistry())
        }
    }

    private fun initAppScope() {
        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        appScope.installModules(AppModule(this))
        val serverDbScope = Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE, DI.DB_SCOPE)
        serverDbScope.installModules(ServerModule(SERVER_PATH), DbModule())
    }
}