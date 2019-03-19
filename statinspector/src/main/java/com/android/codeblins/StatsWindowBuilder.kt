package com.android.codeblins

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.android.codeblins.statinspector.views.StatsWindow

/**
 * Created by Codeblin S. on 3/10/2019.
 */

enum class StatTrackType{
    FULL,
    CPU,
    BATTERY,
    NETWORK
}

class IllegalContextException(message: String): Exception(message)

class StatsWindowBuilder(private val context: Context) {
    private var trackType: StatTrackType = StatTrackType.FULL
    private var logs: Boolean = false

    fun type(trackType: StatTrackType): StatsWindowBuilder{
        this.trackType = trackType
        return this
    }

    fun withLogs(): StatsWindowBuilder{
        this.logs = true
        return this
    }

    fun create() {
        val args = Bundle().apply {
            putBoolean(StatsWindow.ARGS_HAS_LOGS, logs)
        }

        if (context is FragmentActivity){
            context.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, StatsWindow.newInstance(args))
                .addToBackStack(null)
                .commit()
        }else{
            throw IllegalContextException("FragmentActivity is required for context")
        }
    }
}