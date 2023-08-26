package ru.gozerov.data.cache

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun writeString(key: String, value: String) {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun readString(key: String) : String? {
        return sharedPreferences.getString(key, null)
    }

}