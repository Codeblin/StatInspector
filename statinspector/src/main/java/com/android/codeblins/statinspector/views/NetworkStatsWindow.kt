package com.android.codeblins.statinspector.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.Initializator.Companion.ARGS_HAS_LOGS
import com.android.codeblins.core.StatInspector
import com.android.codeblins.statinspector.R
import com.android.codeblins.statinspector.models.BaseStatModel
import com.android.codeblins.statinspector.models.NetworkStatsModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.window_network_stat_info.*

/**
 * Created by Codeblin S. on 3/10/2019.
 */
class NetworkStatsWindow : BaseFragment() {

    companion object: Initializator()

    override fun getLayoutId(): Int = R.layout.window_network_stat_info

    override fun getDisposable(): Disposable = StatInspector.statSubject.subscribe({
        txtNetworkTrafficTitle.text = "Received: ${it.rx.text} \n Transmitted: ${it.rt.text}"
    }, {
        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
    })
}