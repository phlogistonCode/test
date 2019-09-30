package com.appricot.test.di

import android.app.Application
import com.appricot.test.TestApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        TestAppModule::class,
        NetworkModule::class,
        DataBaseModule::class,
        ActivityBindingModule::class]
)
interface TestAppComponent : AndroidInjector<TestApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun testAppModule(tam: TestAppModule): Builder

        fun build(): TestAppComponent
    }

}