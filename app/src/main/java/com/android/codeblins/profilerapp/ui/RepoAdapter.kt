package com.android.codeblins.profilerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.codeblins.profilerapp.R
import com.android.codeblins.profilerapp.models.Repo

/**
 * Created by Codeblin S. on 3/10/2019.
 */

class RepoAdapter : ListAdapter<Repo, RepoItemViewHolder>(RepoDiffUtils()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoItemViewHolder(inflater.inflate(R.layout.row_repo_item, parent, false))
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class RepoDiffUtils : DiffUtil.ItemCallback<Repo>(){
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return false
    }
}