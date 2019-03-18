package com.android.codeblins.profilerapp.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.codeblins.profilerapp.models.Repo
import kotlinx.android.synthetic.main.row_repo_item.view.*

/**
 * Created by Codeblin S. on 3/10/2019.
 */

class RepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(repo: Repo){
        itemView.title.text = repo.name
    }
}