package com.appricot.test.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.appricot.test.details.DetailsFragment
import com.appricot.test.list.ListFragment
import com.appricot.test.utils.*
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val activity: MainActivity,
    private val listFragment: ListFragment,
    private val detailsFragment: DetailsFragment
) : MainContract.Presenter {

    private var activeFragment: Fragment = listFragment

    fun toListFragment() {
        activity.changeFragment(activeFragment, listFragment)
        activeFragment = listFragment
    }

    fun toDetailsFragment(infoToFragment: String?) {
        if (!infoToFragment.isNullOrEmpty()) {
            val bundle = Bundle()
            bundle.putString("id", infoToFragment)
            detailsFragment.arguments = bundle
        }
        activity.changeFragmentBackStack(detailsFragment)
        activeFragment = detailsFragment
    }

    fun loadFragments() {
        activity.clearBackStack()
        activity.addFragment(listFragment)
        activity.showFragment(listFragment)
    }
}