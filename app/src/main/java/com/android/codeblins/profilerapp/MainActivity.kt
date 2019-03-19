package com.android.codeblins.profilerapp

import android.app.ActivityManager
import android.content.Context
import android.net.TrafficStats
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import com.android.codeblins.StatTrackType
import com.android.codeblins.StatsWindowBuilder
import com.android.codeblins.profilerapp.apiclient.ApiManager
import com.android.codeblins.profilerapp.models.Repo
import com.android.codeblins.profilerapp.ui.RepoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.android.codeblins.profilerapp.apiclient.GitHubService


class MainActivity : AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUserRepo.addTextChangedListener(this)
        recycler.adapter = RepoAdapter()

        StatsWindowBuilder(this)
            .type(StatTrackType.NETWORK)
            .create()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val repos = ApiManager.createService(GitHubService::class.java, "codeblin", "togrou66!!").listRepos(s?.toString() ?: "")

        repos.enqueue(object : Callback<List<Repo>>{
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                print("failed")
            }

            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                (recycler.adapter as RepoAdapter).submitList(response.body())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .clearApplicationUserData()
    }
}

