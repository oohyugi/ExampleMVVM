package com.yogi.examplemvvm.data

import com.yogi.examplemvvm.BuildConfig
import com.yogi.examplemvvm.data.remote.ApiService
import com.yogi.examplemvvm.data.remote.MyResult
import com.yogi.examplemvvm.model.GithubUserMdl


/**
 * Created by oohyugi on 2019-08-31.
 * github: https://github.com/oohyugi
 */
interface Repository {


    suspend fun fetchListUser(username: String, page: Int): MyResult<List<GithubUserMdl>>

    class RepositoryImpl : Repository {


        private val mService: ApiService by lazy {
            ApiService.makeApiServices(BuildConfig.BASE_URL)
        }

        override suspend fun fetchListUser(
            username: String,
            page: Int
        ): MyResult<List<GithubUserMdl>> {
            return try {
                val request = mService.getListUser(username, page, 10)
                MyResult.Success(request.items)
            } catch (e: Exception) {
                MyResult.Error(e)
            }

        }


    }


}