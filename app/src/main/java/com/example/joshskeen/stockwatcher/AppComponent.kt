package com.example.joshskeen.stockwatcher

import javax.inject.Singleton

import dagger.Component
import com.example.joshskeen.stockwatcher.ui.StockInfoFragment

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(fragment: StockInfoFragment)
}
