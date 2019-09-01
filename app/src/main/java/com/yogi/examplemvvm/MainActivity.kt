package com.yogi.examplemvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yogi.examplemvvm.feature.home.HomeFragment
import com.yogi.examplemvvm.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelShared : SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModelShared = ViewModelProviders.of(this).get(SharedViewModel::class.java)


        replaceFragment(HomeFragment.newInstance(),R.id.frame_container,"home")
        viewModelShared.updateUser.observe(this, Observer {
            tv_username.text = it

        })
    }
}
