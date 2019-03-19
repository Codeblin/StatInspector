package com.android.codeblins.logging

import android.util.Log
import com.android.codeblins.statinspector.BuildConfig

class StatsLogger(private val enabled: Boolean = false) {

    private val TAG = "StatInspector"

    private fun log(action: () -> Unit){
        if (BuildConfig.DEBUG && enabled){
            action.invoke()
        }
    }

    private fun logMessage(tag: String?, message: String?) = "${if(tag == null) "" else "$tag : "} $message"

    fun logError(tag: String? = null, errorMessage: String?){
        log{
            Log.e(TAG, logMessage(tag, errorMessage))
        }
    }

    fun logInfo(tag: String? = null, infoMessage: String?){
        log{
            Log.i(TAG, logMessage(tag, infoMessage))
        }
    }

    fun logWarning(tag: String? = null, warningMessage: String?){
        log{
            Log.w(TAG, logMessage(tag, warningMessage))
        }
    }
}