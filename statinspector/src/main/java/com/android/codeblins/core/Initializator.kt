package com.android.codeblins.core

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Codeblin S. on 3/20/2019.
 */

abstract class Initializator {
    companion object {
        const val ARGS_HAS_LOGS = "ARGS_HAS_LOGS"
    }

    inline fun <reified T : Fragment> newInstance(args: Bundle): T {
        val fragment = T::class.java.newInstance()
        fragment.arguments = args
        return fragment
    }
}