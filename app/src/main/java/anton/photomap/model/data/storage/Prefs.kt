package anton.photomap.model.data.storage

import android.content.Context
import anton.photomap.model.data.sharedPrefs.SharedPrefs
import javax.inject.Inject

class Prefs @Inject constructor(
        private val context: Context
) : SharedPrefs {

    private val SHARED_PREFS = "shared_prefs"
    private val KEY_DATA_CHANGED = "ad_data_changed"
    private val KEY_FIRST_TIME_LOADING = "ad_first_time_loading"

    private fun getSharedPreferences(prefsName: String) =
            context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override var isDataChanged: Boolean
        get() = getSharedPreferences(SHARED_PREFS).getBoolean(KEY_DATA_CHANGED, false)
        set(value) {
            getSharedPreferences(SHARED_PREFS).edit().putBoolean(KEY_DATA_CHANGED, value).apply()
        }

    override var isFirstTimeLoading: Boolean
        get() = getSharedPreferences(SHARED_PREFS).getBoolean(KEY_FIRST_TIME_LOADING, true)
        set(value) {
            getSharedPreferences(SHARED_PREFS).edit().putBoolean(KEY_FIRST_TIME_LOADING, value).apply()
        }
}