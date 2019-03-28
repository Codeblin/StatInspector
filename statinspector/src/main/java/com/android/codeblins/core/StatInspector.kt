package com.android.codeblins.core

import com.android.codeblins.logging.StatsLogger
import com.android.codeblins.models.BaseStatModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

/**
 * Created by Codeblin S. on 3/24/2019.
 */

abstract class StatInspector{
    private val INSPECTION_TICKS = 300L

    protected var uid: Int = 0

    protected lateinit var statLogger: StatsLogger

    protected var statInspectionDisposable: Disposable? = null

    val statSubject: BehaviorSubject<BaseStatModel> = BehaviorSubject.create()

    abstract fun initializeInspection()

    fun init(uid: Int, withLog: Boolean = false){
        this.statLogger =  StatsLogger(withLog)
        this.uid = uid

        initializeInspection()
    }

    abstract fun calculationAction(): BaseStatModel

    fun startInspection(){
        statLogger.logInfo(infoMessage = "Inspection Starts...")
        statInspectionDisposable?.dispose()
        statInspectionDisposable = null
        statInspectionDisposable = Observable.interval(INSPECTION_TICKS, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                val model = calculationAction()

                statSubject.onNext(model)
                statLogger.logInfo("Network Traffic", model.toString())
            }
    }

    fun stopInspection(){
        statInspectionDisposable?.dispose()
        statLogger.logInfo(infoMessage = "Inspection Stopped...")
    }
}