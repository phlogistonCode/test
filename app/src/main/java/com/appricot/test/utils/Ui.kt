package com.appricot.test.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

fun AppCompatActivity.changeFragment(hideFragment: Fragment, showFragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .hide(hideFragment)
        .show(showFragment)
        .commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .add(fragment_container.id, fragment, fragment.javaClass.simpleName)
        .hide(fragment)
        .commit()
}

fun AppCompatActivity.hideFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .hide(fragment)
        .commit()
}

fun AppCompatActivity.showFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .show(fragment)
        .commit()
}