package com.android.codeblins.builders

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.codeblins.statinspector.views.NetworkStatsWindow

/**
 * Created by Codeblin S. on 3/20/2019.
 */
class NetworkStatWindowBuilder(context: Context) : InspectorWindowBuilder(context) {
    override fun getWindowWithArguments(args: Bundle): Fragment = NetworkStatsWindow.newInstance<NetworkStatsWindow>(args)
}