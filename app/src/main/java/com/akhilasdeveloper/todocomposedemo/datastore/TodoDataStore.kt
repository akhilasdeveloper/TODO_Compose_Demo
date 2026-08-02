package com.akhilasdeveloper.todocomposedemo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.akhilasdeveloper.todocomposedemo.common.ThemeMode
import kotlinx.coroutines.flow.map

internal val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "todo_settings"
)

class TodoDataStore(private val context: Context) {
    private val themeModeKey = intPreferencesKey("theme_mode_key")

    fun getThemeMode() = context.userPreferencesDataStore.data.map {
        ThemeMode.parse(it[themeModeKey])
    }

    suspend fun setThemeMode(themeMode: ThemeMode){
        context.userPreferencesDataStore.edit { preferences ->
            preferences[themeModeKey] = themeMode.value
        }
    }
}