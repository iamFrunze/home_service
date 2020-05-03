package com.byfrunze.homeservice.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.byfrunze.homeservice.R
import com.byfrunze.homeservice.presenters.PresenterOnlineReg
import com.byfrunze.homeservice.views.ViewOnlineReg
import kotlinx.android.synthetic.main.fragment_online_reg.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * A simple [Fragment] subclass.
 */
class OnlineRegFragment : MvpAppCompatFragment(), ViewOnlineReg {

    @InjectPresenter
    lateinit var presenter: PresenterOnlineReg

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_reg, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mask = MaskImpl(PredefinedSlots.RUS_PHONE_NUMBER, true)
        val watcher = MaskFormatWatcher(mask)
        watcher.installOn(edt_tel_num_reg)

        var number = ""
        edt_tel_num_reg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                number = s.toString().replace("(", "")
                    .replace(")", "").replace("-", "").replace(" ", "")
            }
        })


        var serviceList = ArrayList<String>()
        mcv_avr_reg.setOnClickListener {
            cb_avr.isChecked = !cb_avr.isChecked

        }
        mcv_domophone_reg.setOnClickListener {
            cb_domophone_reg.isChecked = !cb_domophone_reg.isChecked

        }
        mcv_el_montaj_reg.setOnClickListener {
            cb_el_montaj_reg.isChecked = !cb_el_montaj_reg.isChecked

        }
        mcv_montaj_reg.setOnClickListener {
            cb_montaj_reg.isChecked = !cb_montaj_reg.isChecked


        }
        btn_send_message_reg.setOnClickListener {
            val secondName = edt_secondname_reg.text.toString()
            val firstName = edt_firstname_reg.text.toString()
            val address = edt_address_reg.text.toString()
            val dopInfo = edt_dopinfo_reg.text.toString()

            if (secondName.isEmpty() ||
                firstName.isEmpty() ||
                number.isEmpty()
            ) {
                MaterialDialog(requireContext()).show {
                    title(R.string.error)
                    message(text = "Заполните поля")
                    icon(R.drawable.ic_uncheck)
                }
            } else
                presenter.sendMessage(
                    secondName = secondName,
                    firstName = firstName,
                    number = number,
                    address = address,
                    dopInfo = dopInfo,
                    service = checkedService(serviceList).toString()
                )
        }

    }

    private fun checkedService(serviceList: ArrayList<String>): ArrayList<String> {
        if (cb_avr.isChecked)
            serviceList.add("Ремонт АВР")
        else serviceList.remove("Ремонт АВР")
        if (cb_domophone_reg.isChecked)
            serviceList.add("Монтаж комбинированного многоквартирного аудио- видеодомофона")
        else serviceList.remove("Монтаж комбинированного многоквартирного аудио- видеодомофона")
        if (cb_el_montaj_reg.isChecked)
            serviceList.add("Электромонтажные работы")
        else serviceList.remove("Электромонтажные работы")
        if (cb_montaj_reg.isChecked)
            serviceList.add("Монтаж вводно-распределительного устройства")
        else serviceList.remove("Монтаж вводно-распределительного устройства")
        return serviceList
    }

    override fun load() {
        cpv_sign_in_loader_reg.visibility = View.VISIBLE
        layout_content_reg.visibility = View.GONE
    }

    override fun errorSend(errorText: String?) {
        MaterialDialog(requireContext()).show {
            title(R.string.error)
            message(text = errorText)
            icon(R.drawable.ic_uncheck)
        }
        layout_content_reg.visibility = View.VISIBLE
        cpv_sign_in_loader_reg.visibility = View.GONE
    }

    override fun successSend() {
        MaterialDialog(requireContext()).show {
            title(R.string.successful)
            message(R.string.send_success)
            icon(R.drawable.ic_check)
        }
        layout_content_reg.visibility = View.VISIBLE
        cpv_sign_in_loader_reg.visibility = View.GONE

    }
}
