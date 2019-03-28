package com.android.codeblins.views

import android.widget.Toast
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.NetworkStatInspector
import com.android.codeblins.statinspector.R
import io.reactivex.disposables.Disposable

/**
 * Created by Codeblin S. on 3/10/2019.
 */
class StatsWindow : BaseFragment() {

    companion object: Initializator()

    override fun getLayoutId(): Int = R.layout.window_stat_info

    override fun getDisposable(): Disposable = NetworkStatInspector.statSubject.subscribe({
        // do something
    }, {
        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
    })
}