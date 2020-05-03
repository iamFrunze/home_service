package com.byfrunze.homeservice.providers

import android.util.Log
import com.byfrunze.homeservice.helper.SMSService
import com.byfrunze.homeservice.presenters.PresenterFeedBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Type

class ProviderFeedBack(private val presenter: PresenterFeedBack) {

    private val TO = "79023854440"
    private val USER = "byfrunze@gmail.com"
    private val PASSWORD = "cSkIJyZbjtWoe2QH93gsmvjwOTvO"
    private val FROM = "SMS Aero"
    private val TYPE = "4"
    private val ANSWER = "json"

    private val apiService by lazy {
        SMSService.create()
    }
    var disposable: Disposable? = null

    fun sendMessage(
        secondName: String,
        firstName: String,
        telNumber: String,
        report: String
    ) {
        val text = "Обратная связь:\n" +
                "Фамилия - $secondName\n" +
                "Имя - $firstName\n" +
                "Номер телефона - $telNumber\n" +
                "Заявка - $report"
        disposable = apiService.sendSMS(
            user = USER,
            password = PASSWORD,
            to = TO,
            text = text,
            from = FROM,
            type = TYPE,
            answer = ANSWER
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.result == "accepted") presenter.onSuccess()
                else presenter.onError("Что-то пошло не так")
            }, {
                presenter.onError(it.localizedMessage)
            })
    }
}