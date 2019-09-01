package com.yogi.examplemvvm.data.remote

import com.yogi.examplemvvm.BuildConfig
import com.yogi.examplemvvm.model.GithubBaseMdl
import com.yogi.examplemvvm.model.GithubUserMdl


/**
 * Created by oohyugi on 2019-08-31.
 * github: https://github.com/oohyugi
 */
class RemoteDataSource {

    private val mService: ApiService by lazy {
        ApiService.makeApiServices(BuildConfig.BASE_URL)
    }

    suspend fun fetchListUser(userName: String, page: Int) = mService.getListUser(userName,page,10)

}