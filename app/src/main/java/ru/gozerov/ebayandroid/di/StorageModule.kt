package ru.gozerov.ebayandroid.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.gozerov.data.utils.dispatchers.Constants

@Module
class StorageModule {

    @Provides
    fun provideSharedPreferences(context: Context) : SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideToken(sharedPreferences: SharedPreferences) : String {
        return sharedPreferences.getString(Constants.API_LOGIN_TOKEN, "") ?: ""
    }

}