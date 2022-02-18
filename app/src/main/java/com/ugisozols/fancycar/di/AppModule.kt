package com.ugisozols.fancycar.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.ugisozols.fancycar.data.local.CarOwnerDao
import com.ugisozols.fancycar.data.local.CarOwnerDatabase
import com.ugisozols.fancycar.data.remote.DriverApi
import com.ugisozols.fancycar.data.repository.CarOwnerRepositoryImpl
import com.ugisozols.fancycar.domain.repository.CarOwnerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCarOwnerDatabase(app: Application): CarOwnerDatabase {
        return Room.databaseBuilder(
            app,
            CarOwnerDatabase::class.java,
            CarOwnerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDriverApi(): DriverApi {
        return Retrofit.Builder()
            .baseUrl(DriverApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DriverApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCarOwnerRepository(
        db : CarOwnerDatabase,
        driverApi: DriverApi
    ) : CarOwnerRepository{
        return CarOwnerRepositoryImpl(db.dao, driverApi)
    }
}