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
import com.google.gson.Gson
import com.yogi.examplemvvm.R
import com.yogi.examplemvvm.SharedViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val TAG = HomeFragment::class.java.name

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelShared: SharedViewModel
    lateinit var mAdapter: UserListAdapter
    private var mPage = 1

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
        initUSer()
        initObserver()


        btn_change_username.setOnClickListener {
            mPage++
            viewModel.fecthData(mPage)
        }
    }

    private fun initObserver() {
        viewModel.mListUser.observe(this, Observer {
            Log.wtf(TAG, "success ${Gson().toJson(it)}")

            it?.let {
                mAdapter.submitList(it)
            }


        })

        viewModel.loading.observe(this, Observer {
            progressbar?.visibility = if (it) View.VISIBLE else View.GONE
        })


    }

    private fun initUSer() {
        mAdapter = UserListAdapter()
        rv_user?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

    }


}
