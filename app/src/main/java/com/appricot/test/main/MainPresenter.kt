package com.appricot.test.main

import androidx.fragment.app.Fragment
import com.appricot.test.details.DetailsFragment
import com.appricot.test.list.ListFragment
import com.appricot.test.utils.addFragment
import com.appricot.test.utils.changeFragment
import com.appricot.test.utils.showFragment
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val activity: MainActivity,
    private val listFragment: ListFragment,
    private val detailsFragment: DetailsFragment
) : MainContract.Presenter {

    private var activeFragment: Fragment = listFragment

    fun toNotesFragment() {
        activity.changeFragment(activeFragment, listFragment)
        activeFragment = listFragment
    }

    fun toPlanningFragment() {
        activity.changeFragment(activeFragment, detailsFragment)
        activeFragment = detailsFragment
    }

    fun loadFragments() {
        activity.addFragment(listFragment)
        activity.showFragment(listFragment)
    }
}