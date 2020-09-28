package com.javadsh98.movie.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.javadsh98.movie.BuildConfig
import com.javadsh98.movie.data.remote.api.Api
import com.javadsh98.movie.data.remote.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    fun getLogger(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
        return logger
    }

    @Provides
    fun getChucker(@ApplicationContext context: Context): ChuckerInterceptor{
        return ChuckerInterceptor(context = context)
    }

    @Provides
    fun getClient(httpLoggingInterceptor: HttpLoggingInterceptor
    , chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .apply {
                if(BuildConfig.DEBUG){
                    addInterceptor(httpLoggingInterceptor)
                }
            }
            .apply {
                if(BuildConfig.DEBUG){
                    addInterceptor(chuckerInterceptor)
                }
            }.build()
    }

    @Provides
    fun getApi(okHttpClient: OkHttpClient): Api{
        return Retrofit.Builder().baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }

    @Provides
    fun getRepo(api: Api): RepositoryImpl {
        return RepositoryImpl(api)
    }


}