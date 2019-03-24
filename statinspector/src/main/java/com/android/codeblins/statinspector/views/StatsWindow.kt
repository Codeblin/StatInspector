package com.android.codeblins.statinspector.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.Initializator.Companion.ARGS_HAS_LOGS
import com.android.codeblins.core.NetworkStatInspector
import com.android.codeblins.statinspector.R
import com.android.codeblins.statinspector.models.NetworkStatsModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.window_stat_info.*

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