package com.blank.protobufsample

import androidx.datastore.core.DataStore
import com.blank.protobufsample.grpc.ListBookResponse
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class PrefRepository(private val userPreferencesStore: DataStore<Preference>,private val bookService: BookService) {

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

   fun getBook() : Flow<ListBookResponse>{
       return bookService.getDataBook()
    }
}