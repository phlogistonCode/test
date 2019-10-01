package com.appricot.test.di

import com.appricot.test.di.scope.PerActivity
import com.appricot.test.main.MainActivity
import com.appricot.test.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [MainModule::class])
    abstract fun provideMainActivity() : MainActivity

}