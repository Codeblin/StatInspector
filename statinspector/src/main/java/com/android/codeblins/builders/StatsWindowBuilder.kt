package com.android.codeblins.builders

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.codeblins.statinspector.views.StatsWindow

/**
 * Created by Codeblin S. on 3/10/2019.
 */

class IllegalContextException(message: String): Exception(message)

class StatsWindowBuilder(context: Context): InspectorWindowBuilder(context) {
    override fun getWindowWithArguments(args: Bundle): Fragment = StatsWindow.newInstance<StatsWindow>(args)
}