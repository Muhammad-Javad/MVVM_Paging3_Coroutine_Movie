package com.javadsh98.movie.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class PreferenceModule {

    @Provides
    fun getPreference(@ApplicationContext context: Context): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

}