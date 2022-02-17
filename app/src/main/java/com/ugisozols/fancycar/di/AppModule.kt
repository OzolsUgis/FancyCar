package com.ugisozols.fancycar.di

import com.ugisozols.fancycar.data.remote.DriverApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDriverApi() : DriverApi {
        return Retrofit.Builder()
            .baseUrl(DriverApi.BASE_URL)
            .build()
            .create(DriverApi::class.java)
    }
}