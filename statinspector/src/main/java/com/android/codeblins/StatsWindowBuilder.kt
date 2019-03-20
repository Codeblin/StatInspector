package com.android.codeblins

import android.content.Context
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

    fun type(trackType: StatTrackType): StatsWindowBuilder{
        this.trackType = trackType
        return this
    }

    fun create() {
        if (context is FragmentActivity){
            context.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, StatsWindow.newInstance())
                .commit()
        }else{
            throw IllegalContextException("FragmentActivity is required for context")
        }
    }
}