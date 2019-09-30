package com.appricot.test.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


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
            .replace(fragment_container.id, fragment, fragment.javaClass.simpleName)
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

fun Long.normalizeTime(): String? {

    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT)
    val calendar = Calendar.getInstance()

    calendar.timeInMillis += this
    return formatter.format(calendar.time)
}

fun String.translateStatus(): String? {
    return when(this) {
        "open" -> "открыта"
        "closed" -> "закрыта"
        "in_progress" -> "занята"
        else -> this
    }
}