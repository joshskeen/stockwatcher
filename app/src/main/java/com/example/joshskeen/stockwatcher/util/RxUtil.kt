package com.example.joshskeen.stockwatcher.util

import android.support.v4.app.FragmentManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.example.joshskeen.stockwatcher.ui.RxFragment
import io.reactivex.Observable

const val LOADING_MESSAGE = "Loading"


fun <T> Observable<T>.applyUIDefaults(rxFragment: RxFragment): Observable<T> =
        addToCompositeDisposable(rxFragment)
                .applySchedulers()
                .applyRequestStatus(rxFragment)
                .showLoadingDialog(rxFragment.fragmentManager)

private fun <T> Observable<T>.addToCompositeDisposable(fragment: RxFragment): Observable<T> =
        doOnSubscribe({ fragment.compositeDisposable.add(it) })

private fun <T> Observable<T>.applySchedulers() =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

private fun <T> Observable<T>.applyRequestStatus(rxFragment: RxFragment) =
        doOnSubscribe({ rxFragment.requestInProgress = true })
                .doOnTerminate({ rxFragment.requestInProgress = false })

private fun <T> Observable<T>.showLoadingDialog(fragmentManager: FragmentManager) =
        doOnSubscribe({ fragmentManager.showProgressDialog(LOADING_MESSAGE) })
                .doOnTerminate({ fragmentManager.hideProgressDialog() })

