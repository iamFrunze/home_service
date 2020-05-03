package com.byfrunze.homeservice.providers

import com.byfrunze.homeservice.helper.SMSService
import com.byfrunze.homeservice.presenters.PresenterOnlineReg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProviderOnlineReg(val presenter: PresenterOnlineReg) {

    private val apiService by lazy {
        SMSService.create()
    }

    var disposable: Disposable? = null

    private val TO = "79023854440"
    private val USER = "byfrunze@gmail.com"
    private val PASSWORD = "cSkIJyZbjtWoe2QH93gsmvjwOTvO"
    private val FROM = "SMS Aero"
    private val TYPE = "4"
    private val ANSWER = "json"
    fun sendMessageByClient(
        secondName: String,
        firstName: String,
        number: String,
        address: String,
        service: String,
        dopInfo: String
    ) {

        val text = "Заявка:\n" +
                "Фамилия - $secondName\n" +
                "Имя - $firstName\n" +
                "Номер телефона - $number\n" +
                "Адрес - $address \n" +
                "Заявка - $service\n" +
                "Дополнительная информация - $dopInfo"
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
