package com.android.codeblins.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.NetworkStatInspector
import com.android.codeblins.statinspector.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.window_base.*

/**
 * Created by Codeblin S. on 3/20/2019.
 */

abstract class BaseFragment: Fragment(){
    private var disposableStatSubscription: Disposable? = null

    abstract fun getLayoutId(): Int

    abstract fun getTitle(): String?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.window_base, container, false)
    }

    abstract fun initLayout(view: View)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasLogs = arguments?.getBoolean(Initializator.ARGS_HAS_LOGS) ?: false

        NetworkStatInspector.init(activity?.applicationInfo?.uid ?: -1, hasLogs)
        NetworkStatInspector.startInspection()

        frameBaseContent.addView(LayoutInflater.from(context).inflate(getLayoutId(), null, false))
        txtBaseTitle.text = getTitle()

        initLayout(view)
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