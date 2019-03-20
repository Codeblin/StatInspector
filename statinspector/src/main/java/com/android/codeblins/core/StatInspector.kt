package com.android.codeblins.core

import android.net.TrafficStats
import com.android.codeblins.logging.StatsLogger
import com.android.codeblins.statinspector.models.NetworkStatsModel
import com.android.codeblins.statinspector.models.StatByte
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


/**
 * Created by Codeblin S. on 3/10/2019.
 */

object StatInspector{
    private const val INSPECTION_TICKS = 300L

    private var startRxBytes: Long = 0
    private var startTxBytes: Long = 0
    private var uid: Int = 0

    private lateinit var statLogger: StatsLogger

    private var statInspectionDisposable: Disposable? = null

    val statSubject: BehaviorSubject<NetworkStatsModel> = BehaviorSubject.create()

    fun init(uid: Int, withLog: Boolean = false){
        statLogger =  StatsLogger(withLog)
        startRxBytes = TrafficStats.getUidRxBytes(uid)
        startTxBytes = TrafficStats.getUidTxBytes(uid)
        StatInspector.uid = uid

        if (startRxBytes.toInt() == TrafficStats.UNSUPPORTED || startRxBytes.toInt() == TrafficStats.UNSUPPORTED) {
            statSubject.onError(Exception("Your device doesn't support traffic monitor"))
            statLogger.logError("On Initialization", "Your device doesn't support traffic monitor")
            return
        }
    }

    fun startInspection(){
        statLogger.logInfo(infoMessage = "Inspection Starts...")
        statInspectionDisposable?.dispose()
        statInspectionDisposable = null
        statInspectionDisposable = Observable.interval(INSPECTION_TICKS, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                val rxBytes = StatByte(TrafficStats.getUidRxBytes(uid) - startRxBytes)
                val txBytes = StatByte(TrafficStats.getUidTxBytes(uid)- startTxBytes)
                val model = NetworkStatsModel(rxBytes, txBytes)

                statSubject.onNext(model)
                statLogger.logInfo("Network Traffic", model.toString())
            }
    }

    fun stopInspection(){
        statInspectionDisposable?.dispose()
        statLogger.logInfo(infoMessage = "Inspection Stopped...")
    }
}