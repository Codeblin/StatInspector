package com.android.codeblins.builders

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.android.codeblins.core.Initializator.Companion.ARGS_HAS_LOGS

/**
 * Created by Codeblin S. on 3/20/2019.
 */
interface InspectorWindowBuilderContract {
    fun withLogs(): InspectorWindowBuilder
    fun create()
}

abstract class InspectorWindowBuilder(private val context: Context) : InspectorWindowBuilderContract{
    private var logs: Boolean = false

    override fun withLogs(): InspectorWindowBuilder {
        this.logs = true
        return this
    }

    abstract fun getWindowWithArguments(args: Bundle): Fragment

    abstract fun getExtraArguments(): Bundle

    override fun create() {
        val args = getExtraArguments()
        args.putBoolean(ARGS_HAS_LOGS, logs)

        if (context is FragmentActivity){
            context.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, getWindowWithArguments(args))
                .commit()
        }else{
            throw IllegalContextException("FragmentActivity is required for context")
        }
    }
}