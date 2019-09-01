package com.yogi.examplemvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by oohyugi on 2019-09-01.
 * github: https://github.com/oohyugi
 */
class SharedViewModel : ViewModel() {

    val updateUser = MutableLiveData<String>()
}