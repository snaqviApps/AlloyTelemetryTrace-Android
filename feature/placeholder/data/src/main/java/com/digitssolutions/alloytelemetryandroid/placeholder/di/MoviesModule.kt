package com.digitssolutions.alloytelemetryandroid.placeholder.di

import com.digitssolutions.alloytelemetryandroid.core.domain.DataPlaceHolderInstance
import com.digitssolutions.alloytelemetryandroid.core.domain.repository.MoviesRepository
import com.digitssolutions.alloytelemetryandroid.placeholder.data.repository.DefaultMoviesRepository
import com.digitssolutions.alloytelemetryandroid.placeholder.data.repository.MoviesRemoteSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object MoviesNetworkModule {

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor {chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesBaseUrl() : String {
        return "https://api.themoviedb.org/3/movie/"
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        baseUrl : String,
        okHttpClient : OkHttpClient
    ) : Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providesMoviesRemoteService(
        retrofit: Retrofit
    ) : MoviesRemoteSource {
        return retrofit
            .create(MoviesRemoteSource::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DefaultMoviesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDefaultMoviesRepository(
        defaultMoviesRepository: DefaultMoviesRepository
    ) : MoviesRepository<DataPlaceHolderInstance>

}

