package com.android.codeblins.statinspector

import android.net.TrafficStats
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
    private const val INSPECTION_TICKS = 2000L

    private var startRxBytes: Long = 0
    private var startTxBytes: Long = 0
    private var uid: Int = 0

    private var statInspectionDisposable: Disposable? = null

    val statSubject: BehaviorSubject<NetworkStatsModel> = BehaviorSubject.create()

    fun init(uid: Int){
        startRxBytes = TrafficStats.getUidRxBytes(uid)
        startTxBytes = TrafficStats.getUidTxBytes(uid)
        this.uid = uid

        if (startRxBytes.toInt() == TrafficStats.UNSUPPORTED || startRxBytes.toInt() == TrafficStats.UNSUPPORTED) {
            statSubject.onError(Exception("Your device doesn't support traffic monitor"))
            return
        }
    }

    fun startInspection(){
        statInspectionDisposable?.dispose()
        statInspectionDisposable = null
        statInspectionDisposable = Observable.interval(INSPECTION_TICKS, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                val rxBytes = StatByte(TrafficStats.getUidRxBytes(uid) - startRxBytes)
                val txBytes = StatByte(TrafficStats.getUidTxBytes(uid)- startTxBytes)

                statSubject.onNext(NetworkStatsModel(rxBytes, txBytes))
            }
    }

    fun stopInspection(){
        statInspectionDisposable?.dispose()
    }
}