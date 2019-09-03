package com.yogi.examplemvvm.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yogi.examplemvvm.base.BaseViewModel
import com.yogi.examplemvvm.data.Repository
import com.yogi.examplemvvm.data.remote.MyResult
import com.yogi.examplemvvm.model.GithubUserMdl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val mRepo = Repository.RepositoryImpl()
    private var _users = MutableLiveData<List<GithubUserMdl>>()
    var mUsername = "doraemon"
    var mPage: Int = 1
    var mListUser: LiveData<List<GithubUserMdl>> = _users
    private var mlist: MutableList<GithubUserMdl> = mutableListOf()

    //navigation
    private val _navigateToDetail = MutableLiveData<GithubUserMdl>()
    val navigateToDetail
        get()
        = _navigateToDetail


    init {


        loadUsers(false, mUsername, mPage)
    }


    private fun loadUsers(refresh: Boolean = false, username: String, page: Int) {

        loading.postValue(true)

        viewModelScope.launch {
            val request = mRepo.fetchListUser(username, page)


            loading.postValue(false)
            withContext(Dispatchers.Main) {
                when (request) {
                    is MyResult.Success -> {
                        mlist.addAll(request.data)
                        _users.value = mlist


                    }
                    is MyResult.Error -> {
                        errorFetchingData.postValue(request.message)

                    }
                }
            }
        }


    }


    fun loadMoreData() {

        mPage++
        loadUsers(true, mUsername, mPage)


    }


    fun onUserItemClicked(it: GithubUserMdl) {
        _navigateToDetail.value = it
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }


}
