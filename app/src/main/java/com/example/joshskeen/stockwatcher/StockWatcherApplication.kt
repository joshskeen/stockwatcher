package com.example.joshskeen.stockwatcher

import android.app.Application

class StockWatcherApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule()).build()
    }

}
