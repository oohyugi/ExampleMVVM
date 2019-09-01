package com.yogi.examplemvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by oohyugi on 2019-09-01.
 * github: https://github.com/oohyugi
 */
open class BaseViewModel: ViewModel() {
    val loading= MutableLiveData<Boolean>()
    val errorFetchingData= MutableLiveData<String>()
}