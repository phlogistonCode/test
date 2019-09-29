package com.appricot.test

import com.appricot.test.di.DaggerTestAppComponent
import com.appricot.test.di.TestAppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerTestAppComponent.builder()
            .application(this)
            .testAppModule(TestAppModule(this))
            .build()
}