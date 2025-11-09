package com.lyciv.star.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "star_prefs")

class FavoriteDao(private val context: Context) {

    private val wallpaperUriKey = stringPreferencesKey("wallpaper_uri")
    private val favoritesKey = stringPreferencesKey("favorites")

    suspend fun saveWallpaperUri(uri: String) {
        context.dataStore.edit { prefs ->
            prefs[wallpaperUriKey] = uri
        }
    }

    fun getWallpaperUri(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[wallpaperUriKey]
        }
    }

    suspend fun saveFavorites(packageNames: List<String>) {
        val joined = packageNames.joinToString(",")
        context.dataStore.edit { prefs ->
            prefs[favoritesKey] = joined
        }
    }

    fun getFavorites(): Flow<List<String>> {
        return context.dataStore.data.map { prefs ->
            val saved = prefs[favoritesKey] ?: ""
            if (saved.isBlank()) emptyList() else saved.split(",")
        }
    }
}
