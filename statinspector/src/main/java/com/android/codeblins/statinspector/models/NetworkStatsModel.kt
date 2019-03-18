package com.android.codeblins.statinspector.models

/**
 * Created by Codeblin S. on 3/10/2019.
 */
data class NetworkStatsModel(val rx: StatByte, val rt: StatByte) {
    val total: StatByte
        get() = StatByte(rx.value + rt.value)
}