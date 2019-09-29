package com.appricot.test.main

import com.appricot.test.details.DetailsFragment
import com.appricot.test.details.DetailsModule
import com.appricot.test.di.scope.PerActivity
import com.appricot.test.di.scope.PerFragment
import com.appricot.test.list.ListFragment
import com.appricot.test.list.ListModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [ListModule::class])
    abstract fun listFragment(): ListFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun detailsFragment(): DetailsFragment

    @PerActivity
    @Binds
    abstract fun mainPresenter(presenter: MainPresenter): MainContract.Presenter

}