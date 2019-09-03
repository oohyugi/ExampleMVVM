package com.yogi.examplemvvm.feature.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yogi.examplemvvm.R
import com.yogi.examplemvvm.model.GithubUserMdl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by oohyugi on 2019-09-01.
 * github: https://github.com/oohyugi
 */
class UserListAdapter(val clickListener: UserListAdapterListener) :
    ListAdapter<UserListAdapter.DataItem, RecyclerView.ViewHolder>(UserListAdapterDiffCallback()) {
    private val TAG = UserListAdapter::class.java.simpleName

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    private var adapterScope = CoroutineScope(Dispatchers.Default)


    fun addHeaderAndSubmitList(list: List<GithubUserMdl>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.UserItem(it) }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.iv_user)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_user)
        val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_user,
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(
            item: GithubUserMdl,
            clickListener: UserListAdapterListener
        ) {
            tvTitle.text = item.login
            tvInfo.text = item.url
            Glide.with(itemView.context).load(item.avatarUrl).override(70, 70).into(img)
            itemView.setOnClickListener {
                clickListener.onClick(item)
            }
        }


    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                return HeaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_header,
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(id: Int) {
            tvTitle.text = id.toString()

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (holder) {
            is ViewHolder -> {
                val itemUser = getItem(position) as DataItem.UserItem
                holder.bind(itemUser.userMdl, clickListener)
            }
            is HeaderViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item.id)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.UserItem -> ITEM_VIEW_TYPE_ITEM
        }
    }


    class UserListAdapterListener(val clickListener: (user: GithubUserMdl) -> Unit) {
        fun onClick(user: GithubUserMdl) = clickListener(user)
    }


    class UserListAdapterDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    sealed class DataItem {
        data class UserItem(val userMdl: GithubUserMdl) : DataItem() {
            override val id = userMdl.id
        }

        object Header : DataItem() {
            override val id = Int.MIN_VALUE
        }

        abstract val id: Int
    }
}