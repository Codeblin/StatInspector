package com.android.codeblins.profilerapp.apiclient

import com.android.codeblins.profilerapp.models.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Codeblin S. on 2/26/2019.
 */

interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}