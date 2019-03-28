package com.android.codeblins.core

import android.net.TrafficStats
import com.android.codeblins.models.BaseStatModel
import com.android.codeblins.models.NetworkStatsModel
import com.android.codeblins.models.StatByte


/**
 * Created by Codeblin S. on 3/10/2019.
 */

object NetworkStatInspector : StatInspector(){

    private var startRxBytes: Long = 0
    private var startTxBytes: Long = 0

    override fun initializeInspection() {
        startRxBytes = TrafficStats.getUidRxBytes(uid)
        startTxBytes = TrafficStats.getUidTxBytes(uid)

        if (startRxBytes.toInt() == TrafficStats.UNSUPPORTED || startRxBytes.toInt() == TrafficStats.UNSUPPORTED) {
            statSubject.onError(Exception("Your device doesn't support traffic monitor"))
            statLogger.logError("On Initialization", "Your device doesn't support traffic monitor")
            return
        }
    }

    override fun calculationAction(): BaseStatModel {
        val rxBytes = StatByte(TrafficStats.getUidRxBytes(uid) - startRxBytes)
        val txBytes = StatByte(TrafficStats.getUidTxBytes(uid)- startTxBytes)
        return NetworkStatsModel(rxBytes, txBytes)
    }
}