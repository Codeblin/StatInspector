package com.android.codeblins.statinspector

import android.app.usage.NetworkStatsManager
import android.net.TrafficStats
import com.android.codeblins.statinspector.models.NetworkStatsModel
import com.android.codeblins.statinspector.models.StatByte
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.lang.Exception
import java.util.concurrent.TimeUnit


/**
 * Created by Codeblin S. on 3/10/2019.
 */

object StatInspector{

    private var startRxBytes: Long = 0
    private var startTxBytes: Long = 0

    private var statInspectionDisposable: Disposable? = null

    val statSubject: BehaviorSubject<NetworkStatsModel> = BehaviorSubject.create()

    fun startInspection(uid: Int){
        startRxBytes = TrafficStats.getUidRxBytes(uid)
        startTxBytes = TrafficStats.getUidTxBytes(uid)

        if (startRxBytes.toInt() == TrafficStats.UNSUPPORTED || startRxBytes.toInt() == TrafficStats.UNSUPPORTED) {
            statSubject.onError(Exception("Your device doesn't support traffic monitor"))
            return
        }

        statInspectionDisposable?.dispose()
        statInspectionDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                val rxBytes = StatByte(TrafficStats.getUidRxBytes(uid))
                val txBytes = StatByte(TrafficStats.getUidTxBytes(uid))

                statSubject.onNext(NetworkStatsModel(rxBytes, txBytes))
            }
    }

    fun stopInspection(){
        statInspectionDisposable?.dispose()
    }
}