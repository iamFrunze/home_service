package com.byfrunze.homeservice.presenters

import com.byfrunze.homeservice.providers.ProviderFeedBack
import com.byfrunze.homeservice.views.ViewFeedBack
import moxy.MvpPresenter

class PresenterFeedBack : MvpPresenter<ViewFeedBack>() {
    fun sendReport(secondName: String, firstName: String, telNumber: String, report: String) {
        viewState.load()
        ProviderFeedBack(this).sendMessage(
            secondName = secondName,
            firstName = firstName,
            telNumber = telNumber,
            report = report
        )
    }

    fun onSuccess() {
        viewState.successfulLoad()
    }

    fun onError(localizedMessage: String?) {
        viewState.errorLoad(localizedMessage)

    }
}