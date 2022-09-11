package com.blank.protobufsample

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class PrefRepository(private val userPreferencesStore: DataStore<Preference>) {

    val preferencesFlow: Flow<Preference> = userPreferencesStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(Preference.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun updatePref(name:String){
        userPreferencesStore.updateData { pref ->
            pref.toBuilder().setName(name).build()
        }
    }
}