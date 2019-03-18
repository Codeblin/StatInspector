package com.android.codeblins.statinspector.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.codeblins.statinspector.R
import com.android.codeblins.statinspector.StatInspector
import com.android.codeblins.statinspector.models.NetworkStatsModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.window_stat_info.*

/**
 * Created by Codeblin S. on 3/10/2019.
 */
class StatsWindow : Fragment() {
    companion object {
        fun newInstance() = StatsWindow()
    }

    private var disposableStatSubscription: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.window_stat_info, container, false)
    }

    override fun onStart() {
        super.onStart()

        disposableStatSubscription = StatInspector.statSubject.subscribe({
            setNetworkTraffic(it)
        }, {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        })

        StatInspector.startInspection(activity?.applicationInfo?.uid ?: -1)
    }

    override fun onStop() {
        super.onStop()

        StatInspector.stopInspection()
        disposableStatSubscription?.dispose()
    }

    private fun setNetworkTraffic(model: NetworkStatsModel){
        txtNetworkTrafficTitle.text = "Received: ${model.rx.text} \n Transmitted: ${model.rt.text}"
    }
}