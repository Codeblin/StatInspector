package com.android.codeblins.models

/**
 * Created by Codeblin S. on 3/10/2019.
 */
data class NetworkStatsModel(val rx: StatByte, val tx: StatByte): BaseStatModel() {
    val total: StatByte
        get() = StatByte(rx.value + tx.value)

    override fun toString(): String = "Received: ${rx.text} \n Transmitted: ${tx.text}"
}