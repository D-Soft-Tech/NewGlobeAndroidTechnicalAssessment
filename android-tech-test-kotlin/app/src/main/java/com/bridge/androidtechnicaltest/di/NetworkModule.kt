package com.bridge.androidtechnicaltest.di

import com.bridge.androidtechnicaltest.data.remoteDataSource.PupilRepositoryImpl
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.AuthInterceptor
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.api.PupilApi
import com.bridge.androidtechnicaltest.di.qualifiers.AppAgentIdScope
import com.bridge.androidtechnicaltest.di.qualifiers.AppRequestIdScope
import com.bridge.androidtechnicaltest.di.qualifiers.AuthInterceptorScope
import com.bridge.androidtechnicaltest.di.qualifiers.IoDispatcherScope
import com.bridge.androidtechnicaltest.di.qualifiers.MainDispatcherScope
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import com.bridge.androidtechnicaltest.utils.AppConstants
import com.bridge.androidtechnicaltest.utils.AppConstants.BASE_URL
import com.bridge.androidtechnicaltest.utils.AppConstants.LONG_30
import com.facebook.shimmer.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesAppBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    @AppAgentIdScope
    fun providesAppAgentId(): String = AppConstants.AGENT_ID

    @Provides
    @Singleton
    @AppRequestIdScope
    fun providesAppRequestId(): String = AppConstants.REQUEST_ID

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    @AuthInterceptorScope
    fun providesAuthInterceptor(
        authInterceptor: AuthInterceptor
    ): Interceptor = authInterceptor

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: Interceptor,
        @AuthInterceptorScope authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(LONG_30, TimeUnit.SECONDS)
        .callTimeout(LONG_30, TimeUnit.SECONDS)
        .writeTimeout(LONG_30, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()

    @Provides
    @Singleton
    fun providesPupilRepository(
        pupilRepositoryImpl: PupilRepositoryImpl
    ): PupilRepository = pupilRepositoryImpl

    @Provides
    @Singleton
    fun providesPupilApiService(
        retrofit: Retrofit
    ): PupilApi = retrofit.create(PupilApi::class.java)

    @Provides
    @Singleton
    @IoDispatcherScope
    fun providesIoDispatcher(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    @MainDispatcherScope
    fun providesMainDispatcher(): CoroutineContext = Dispatchers.Main
}

