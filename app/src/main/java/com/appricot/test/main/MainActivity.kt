package com.appricot.test.main

import android.os.Bundle
import android.util.Log
import com.appricot.test.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject





class MainActivity : DaggerAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainPresenter

    private var mMainFragmentArgs: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Main", "create Activity")
        presenter.loadFragments()
        Log.d("Main", "Fragment Loaded")
    }

    fun saveMainFragmentState(args: Bundle) {
        mMainFragmentArgs = args
    }

    fun getSavedMainFragmentState(): Bundle? {
        return mMainFragmentArgs
    }
}
