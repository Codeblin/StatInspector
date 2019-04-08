package com.android.codeblins.views

import android.view.View
import android.widget.Toast
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.NetworkStatInspector
import com.android.codeblins.statinspector.R
import com.android.codeblins.models.NetworkStatsModel
import com.android.codeblins.models.NetworkTrackOptions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.window_network_stat_info.*

/**
 * Created by Codeblin S. on 3/10/2019.
 */
class NetworkStatsWindow : BaseFragment() {

    companion object: Initializator()

    private var showTransmitted = true
    private var showReceived = true
    private var showFull = showReceived && showTransmitted

    override fun getLayoutId(): Int = R.layout.window_network_stat_info

    private fun handleArguments(){
        arguments?.let {
            showReceived = it.getBoolean(NetworkTrackOptions.Received.code)
            showTransmitted = it.getBoolean(NetworkTrackOptions.Transmitted.code)
            showFull = it.getBoolean(NetworkTrackOptions.Full.code) ||
                    (showReceived && showTransmitted)
        }
    }

    override fun getTitle(): String? = context?.getString(R.string.title_traffic_monitor)

    override fun initLayout(view: View) {
        handleArguments()

        if(showFull){
            txtNetworkTrafficRxValue.visibility = View.VISIBLE
            txtNetworkTrafficRx.visibility = View.VISIBLE

            txtNetworkTrafficTxValue.visibility = View.VISIBLE
            txtNetworkTrafficTx.visibility = View.VISIBLE
        }else{
            if (showReceived){
                txtNetworkTrafficRxValue.visibility = View.VISIBLE
                txtNetworkTrafficRx.visibility = View.VISIBLE

                txtNetworkTrafficTxValue.visibility = View.GONE
                txtNetworkTrafficTx.visibility = View.GONE
            }else{
                txtNetworkTrafficRxValue.visibility = View.GONE
                txtNetworkTrafficRx.visibility = View.GONE

                txtNetworkTrafficTxValue.visibility = View.VISIBLE
                txtNetworkTrafficTx.visibility = View.VISIBLE
            }
        }
    }

    override fun getDisposable(): Disposable = NetworkStatInspector.statSubject.subscribe({
        if(it is NetworkStatsModel){
            txtNetworkTrafficRxValue.text = it.rx.text
            txtNetworkTrafficTxValue.text = it.tx.text
        }
    }, {
        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
    })
}