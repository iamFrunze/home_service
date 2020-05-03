package com.byfrunze.homeservice.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ViewFeedBack : MvpView {
    fun load()
    fun errorLoad(textError: String?)
    fun successfulLoad()
}