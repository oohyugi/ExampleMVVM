package com.yogi.examplemvvm.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yogi.examplemvvm.R
import com.yogi.examplemvvm.model.GithubUserMdl


/**
 * Created by oohyugi on 2019-09-01.
 * github: https://github.com/oohyugi
 */
class UserListAdapter :
    ListAdapter<GithubUserMdl,UserListAdapter.ViewHolder>(UserListAdapterDiffCallback()) {
    private val onItemClickListener: AdapterView.OnItemClickListener? = null
    private val TAG = UserListAdapter::class.java.simpleName


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.iv_user)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_user)
        val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)
        fun bind(item: GithubUserMdl) {
            tvTitle.text = item.login
            tvInfo.text = item.url
            Glide.with(itemView.context).load(item.avatarUrl).override(70, 70).into(img)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val context = parent.context
                val inflater = LayoutInflater.from(context)

                val view = inflater.inflate(R.layout.item_user, parent, false)

                return ViewHolder(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)

    }





    class UserListAdapterDiffCallback : DiffUtil.ItemCallback<GithubUserMdl>() {
        override fun areItemsTheSame(oldItem: GithubUserMdl, newItem: GithubUserMdl): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubUserMdl, newItem: GithubUserMdl): Boolean {
            return oldItem == newItem
        }

    }
}