package com.byfrunze.homeservice.presenters

import com.byfrunze.homeservice.providers.ProviderOnlineReg
import com.byfrunze.homeservice.views.ViewOnlineReg
import moxy.MvpPresenter

class PresenterOnlineReg: MvpPresenter<ViewOnlineReg>() {

    fun sendMessage(
        secondName: String,
        firstName: String,
        number: String,
        address: String,
        service: String,
        dopInfo: String
    ) {
        viewState.load()
        ProviderOnlineReg(this).sendMessageByClient(
            secondName = secondName,
            firstName = firstName,
            number = number,
            address = address,
            service = service,
            dopInfo = dopInfo
        )

    }

    fun onSuccess() {
        viewState.successSend()
    }

    fun onError(s: String?) {
        viewState.errorSend(s)
    }
}