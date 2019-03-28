package com.android.codeblins.builders

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.codeblins.models.NetworkTrackOptions
import com.android.codeblins.views.NetworkStatsWindow

/**
 * Created by Codeblin S. on 3/20/2019.
 */
class NetworkStatWindowBuilder(context: Context) : InspectorWindowBuilder(context) {

    private val trackingOptions: MutableList<NetworkTrackOptions> = mutableListOf()

    override fun getWindowWithArguments(args: Bundle): Fragment = NetworkStatsWindow.newInstance<NetworkStatsWindow>(args)

    override fun getExtraArguments(): Bundle {
        val args = Bundle()
        for(option in trackingOptions){
            when(option){
                NetworkTrackOptions.Received -> args.putBoolean(NetworkTrackOptions.Received.code, true)
                NetworkTrackOptions.Transmitted -> args.putBoolean(NetworkTrackOptions.Transmitted.code, true)
                NetworkTrackOptions.Full -> args.putBoolean(NetworkTrackOptions.Full.code, true)
            }
        }

        return args
    }

    fun trackingOptions(vararg options: NetworkTrackOptions): NetworkStatWindowBuilder{
        trackingOptions.addAll(options)
        return this
    }
}