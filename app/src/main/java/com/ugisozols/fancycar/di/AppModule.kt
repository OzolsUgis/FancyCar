package com.ugisozols.fancycar.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugisozols.fancycar.data.local.CarOwnerDatabase
import com.ugisozols.fancycar.data.remote.driverApi.DriverApi
import com.ugisozols.fancycar.data.repository.CarOwnerRepositoryImpl
import com.ugisozols.fancycar.data.repository.DirectionRepositoryImpl
import com.ugisozols.fancycar.domain.repository.CarOwnerRepository
import com.ugisozols.fancycar.domain.repository.DirectionRepository
import com.ugisozols.fancycar.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideGson() : Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
    @Provides
    @Singleton
    fun provideDriverApi(gson: Gson): DriverApi {
        return Retrofit.Builder()
            .baseUrl(DriverApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
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

    @Provides
    @Singleton
    fun provideDirectionRepository() : DirectionRepository{
        return DirectionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDirection(repository: DirectionRepository) : GetPolylines{
        return GetPolylines(repository)
    }

    @Provides
    @Singleton
    fun provideGetOwnersUseCase(repository : CarOwnerRepository) : GetOwners{
        return GetOwners(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateOwnersVehicles(repository: CarOwnerRepository): UpdateOwnersVehicles {
        return UpdateOwnersVehicles(repository)
    }

    @Provides
    @Singleton
    fun provideDecodeCoordinates(context: Application) : DecodeCoordinatesToAddress{
        return DecodeCoordinatesToAddress(context)
    }

    @Provides
    @Singleton
    fun provideDecodeColorFromString() : DecodeColorFromString{
        return DecodeColorFromString()
    }

    @Provides
    @Singleton
    fun provideContext(context: Application): Context{
        return context
    }
}