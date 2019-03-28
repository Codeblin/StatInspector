package com.android.codeblins.views

import android.widget.Toast
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.NetworkStatInspector
import com.android.codeblins.statinspector.R
import com.android.codeblins.models.NetworkStatsModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.window_network_stat_info.*

/**
 * Created by Codeblin S. on 3/10/2019.
 */
class NetworkStatsWindow : BaseFragment() {

    companion object: Initializator()

    override fun getLayoutId(): Int = R.layout.window_network_stat_info

    override fun getDisposable(): Disposable = NetworkStatInspector.statSubject.subscribe({
        if(it is NetworkStatsModel){
            txtNetworkTrafficTitle.text = "Received: ${it.rx.text} \n Transmitted: ${it.rt.text}"
        }
    }, {
        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
    })
}