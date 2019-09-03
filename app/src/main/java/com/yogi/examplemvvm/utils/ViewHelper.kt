package com.yogi.examplemvvm.utils

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Created by oohyugi on 2019-09-01.
 * github: https://github.com/oohyugi
 */

fun FragmentActivity.replaceFragment(fragment: Fragment, idContainer: Int, tag: String?) {
    supportFragmentManager.beginTransaction()
        .replace(idContainer,fragment,tag)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()

}
fun FragmentActivity.replaceFragmentAndHide(fragment: Fragment, idContainer: Int, tag: String?, hideFragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .add(idContainer,fragment,tag)
        .hide(hideFragment)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()

}
fun FragmentActivity.addFragment(fragment: Fragment, idContainer: Int, tag: String?) {
    supportFragmentManager.beginTransaction()
        .add(idContainer,fragment,tag)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()

}


fun FragmentManager.switch(containerId: Int, newFrag: Fragment, tag: String) {

    var current = findFragmentByTag(tag)
    beginTransaction()
        .apply {

            //Hide the current fragment
            primaryNavigationFragment?.let { hide(it) }

            //Check if current fragment exists in fragmentManager
            if (current == null) {
                current = newFrag
                add(containerId, current!!, tag)
            } else {
                show(current!!)
            }
        }
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .setPrimaryNavigationFragment(current)
        .setReorderingAllowed(true)
        .commitNowAllowingStateLoss()
}


fun Context.toast(message:String?){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
