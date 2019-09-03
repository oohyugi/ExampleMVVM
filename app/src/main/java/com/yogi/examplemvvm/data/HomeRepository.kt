package com.yogi.examplemvvm.data

import android.util.Log
import com.google.gson.Gson
import com.yogi.examplemvvm.data.remote.RemoteDataSource
import com.yogi.examplemvvm.data.remote.ResultState
import com.yogi.examplemvvm.model.GithubUserMdl
import com.yogi.examplemvvm.utils.fetchState


/**
 * Created by oohyugi on 2019-08-31.
 * github: https://github.com/oohyugi
 */
interface HomeRepository {


    suspend fun fetchListUser(username: String, page: Int): ResultState<List<GithubUserMdl>>

    class HomeRepositoryImpl : HomeRepository {

        val dataSource = RemoteDataSource()
        override suspend fun fetchListUser(
            username: String,
            page: Int
        ): ResultState<List<GithubUserMdl>> {

            return fetchState {
                val request = dataSource.fetchListUser(username, page)
                if (request.isSuccessful) {
                    ResultState.Success(request.body()?.items)
                } else {
                    Log.wtf("error response", Gson().toJson(request.raw().networkResponse()))
                    ResultState.Error(request.message())
                }


            }


        }
    }


}