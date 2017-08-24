package com.example.joshskeen.stockwatcher.ui

import android.os.Bundle
import android.support.v4.app.Fragment

import io.reactivex.disposables.CompositeDisposable

const val EXTRA_RX_REQUEST_IN_PROGRESS = "EXTRA_RX_REQUEST_IN_PROGRESS"

abstract class RxFragment : Fragment() {

    private var Bundle.requestInProgress: Boolean
        get() = getBoolean(EXTRA_RX_REQUEST_IN_PROGRESS, false)
        set(inProgress) = putBoolean(EXTRA_RX_REQUEST_IN_PROGRESS, inProgress)

    var requestInProgress = false

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            requestInProgress = savedInstanceState.requestInProgress
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.requestInProgress = requestInProgress
    }

    override fun onResume() {
        super.onResume()
        if (requestInProgress) loadRxData()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    abstract fun loadRxData()

}
