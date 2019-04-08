package com.android.codeblins.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.android.codeblins.core.Initializator
import com.android.codeblins.core.NetworkStatInspector
import com.android.codeblins.statinspector.R
import com.android.codeblins.utils.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.window_base.*

/**
 * Created by Codeblin S. on 3/20/2019.
 */

abstract class BaseFragment: Fragment(), View.OnClickListener, OnSwipeTouchListener.SwipeDirectionListener {
    private var disposableStatSubscription: Disposable? = null
    protected var isShown = true

    // =====================================================
    // Abstracts
    // =====================================================

    abstract fun getLayoutId(): Int

    abstract fun getTitle(): String?

    abstract fun initLayout(view: View)

    abstract fun getDisposable() : Disposable

    // =====================================================
    // Lifecycle
    // =====================================================

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.window_base, container, false)
    }

    // Initializes base window layout functionality
    private fun baseLayoutInit(){
        frameBaseContent.addView(LayoutInflater.from(context).inflate(getLayoutId(), null, false))
        txtBaseTitle.text = getTitle()

        imgBaseArrow.setOnClickListener(this)

        context?.let {
            view?.setOnTouchListener(OnSwipeTouchListener(it, this))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasLogs = arguments?.getBoolean(Initializator.ARGS_HAS_LOGS) ?: false

        NetworkStatInspector.init(activity?.applicationInfo?.uid ?: -1, hasLogs)
        NetworkStatInspector.startInspection()

        baseLayoutInit()

        // Child fragment initializes its layout views here
        initLayout(view)
    }

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

    // =====================================================
    // Actions
    // =====================================================

    private fun showMonitor(show: Boolean){
        val radius = if(show) 180f else 0f
        Animations.rotate(imgBaseArrow, radius)

        if(show){
            cntBaseMonitor.visibility = View.VISIBLE
        }else{
            cntBaseMonitor.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBaseArrow -> {
                isShown = !isShown
                showMonitor(isShown)
            }
        }
    }

    override fun onSwipe(direction: Direction) {
        isShown = direction == Direction.DOWN
        showMonitor(isShown)
    }
}