package com.android.codeblins.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.NetworkStatInspector
import io.reactivex.disposables.Disposable

/**
 * Created by Codeblin S. on 3/20/2019.
 */

abstract class BaseFragment: Fragment(){
    private var disposableStatSubscription: Disposable? = null

    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasLogs = arguments?.getBoolean(Initializator.ARGS_HAS_LOGS) ?: false

        NetworkStatInspector.init(activity?.applicationInfo?.uid ?: -1, hasLogs)
        NetworkStatInspector.startInspection()
    }

    abstract fun getDisposable() : Disposable

    override fun onStart() {
        super.onStart()

        disposableStatSubscription = getDisposable()
    }

    override fun onStop() {
        super.onStop()
        disposableStatSubscription?.dispose()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        NetworkStatInspector.stopInspection()
    }
}