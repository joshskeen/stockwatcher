package com.example.joshskeen.stockwatcher

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.joshskeen.stockwatcher.service.ContentTypeHeaderInterceptor
import com.example.joshskeen.stockwatcher.service.ServiceConfig
import com.example.joshskeen.stockwatcher.service.StockService
import com.example.joshskeen.stockwatcher.service.repository.StockDataRepository


private const val STOCK_SERVICE_ENDPOINT = "http://dev.markitondemand.com/MODApis/Api/v2/"
private const val HTTP_READ_TIMEOUT = 60
private const val HTTP_CONNECT_TIMEOUT = 60

@Module
internal class AppModule {

    @Provides
    @Singleton
    fun provideStockDataRepository(stockService: StockService): StockDataRepository =
            StockDataRepository(stockService)

    @Provides
    fun provideStockService(retrofit: Retrofit): StockService = StockService(retrofit)

    @Provides
    fun provideServiceConfig() = ServiceConfig(STOCK_SERVICE_ENDPOINT)

    @Provides
    fun provideRetrofit(client: OkHttpClient, serviceConfig: ServiceConfig) =
            Retrofit.Builder()
                    .baseUrl(serviceConfig.baseServiceUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()!!

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
            .readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ContentTypeHeaderInterceptor())
            .build()

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}
