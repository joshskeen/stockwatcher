package com.example.joshskeen.stockwatcher.extensions

import android.content.Context
import android.support.v4.app.Fragment
import com.example.joshskeen.stockwatcher.AppComponent
import com.example.joshskeen.stockwatcher.StockWatcherApplication

val Context.component: AppComponent
    get() = (applicationContext as StockWatcherApplication).appComponent

val Fragment.component: AppComponent
    get() = activity.component