package ru.gozerov.ebayandroid.app

import android.app.Application
import android.content.Context
import ru.gozerov.ebayandroid.di.AppComponent
import ru.gozerov.ebayandroid.di.DaggerAppComponent

class EbayApp: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

}

val Context.appComponent: AppComponent
    get() {
        return if (this is EbayApp) this.appComponent
        else (this.applicationContext as EbayApp).appComponent
    }