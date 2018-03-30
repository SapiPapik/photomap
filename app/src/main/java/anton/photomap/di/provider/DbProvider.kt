package anton.photomap.di.provider

import android.content.Context
import anton.photomap.model.data.db.Models
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.reactivex.KotlinReactiveEntityStore
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.TableCreationMode
import javax.inject.Inject
import javax.inject.Provider


class DbProvider @Inject constructor(
        private val context: Context
) : Provider<KotlinReactiveEntityStore<Persistable>> {

    override fun get(): KotlinReactiveEntityStore<Persistable> {
        val source = DatabaseSource(context, Models.DEFAULT, 1)
        source.setTableCreationMode(TableCreationMode.DROP_CREATE)
        return KotlinReactiveEntityStore<Persistable>(KotlinEntityDataStore(source.configuration))
    }
}