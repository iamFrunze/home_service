package com.byfrunze.homeservice.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ViewOnlineReg: MvpView {
    fun load()
    fun errorSend(errorText: String?)
    fun successSend()
}