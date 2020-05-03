package com.byfrunze.homeservice.helper

import com.byfrunze.homeservice.models.SMSModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SMSService {

    @GET("send/")
    fun sendSMS(
        @Query("user") user: String,
        @Query("password") password: String,
        @Query("to") to: String,
        @Query("text") text: String,
        @Query("from") from: String,
        @Query("type") type: String,
        @Query("answer") answer: String
    ): Observable<SMSModel>

    companion object Factory {
        private const val BASE_URL =
            "https://gate.smsaero.ru/"

        fun create(): SMSService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(SMSService::class.java)
        }
    }
}