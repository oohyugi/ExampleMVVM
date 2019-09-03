package com.yogi.examplemvvm.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.yogi.examplemvvm.R
import com.yogi.examplemvvm.SharedViewModel
import com.yogi.examplemvvm.utils.*
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val TAG = HomeFragment::class.java.name

    private var mPage =0
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelShared: SharedViewModel
    lateinit var mAdapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
        viewModelShared = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        initUser()
        initObserver()


        btn_change_username.setOnClickListener {
            mPage++
            context?.toast(mPage.toString())
            viewModel.loadMoreData()
        }

        context?.toast("connectivity ${context?.isConnectedWifi()}")
        val aa ="sfsf"
    }

    private fun initObserver() {
        viewModel.mListUser.observe(this, Observer {
//            Log.wtf(TAG, "success ${Gson().toJson(it)}")

            it?.let {
                mAdapter.addHeaderAndSubmitList(it)
            }


        })

        viewModel.loading.observe(this, Observer {
            progressbar?.visibility = if (it) View.VISIBLE else View.GONE
        })


        viewModel.errorFetchingData.observe(this, Observer {
            context?.toast(it)
        })
        viewModel.navigateToDetail.observe(this, Observer {
            it?.let {
                //todo intent to detail

                context?.toast("todo goto detail")

                viewModel.onDetailNavigated()
            }
        })


    }

    private fun initUser() {
        mAdapter = UserListAdapter(UserListAdapter.UserListAdapterListener {
            context?.toast(it.login)
            viewModel.onUserItemClicked(it)
        })
        val mLayoutManager = LinearLayoutManager(context)
        val scrollListener = object :EndlessRecyclerViewScrollListener(mLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.loadMoreData()
            }

        }
        rv_user?.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addOnScrollListener(scrollListener)

        }

    }

}
