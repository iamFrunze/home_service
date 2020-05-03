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
import com.byfrunze.homeservice.presenters.PresenterFeedBack
import com.byfrunze.homeservice.views.ViewFeedBack
import kotlinx.android.synthetic.main.fragment_feed_back.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * A simple [Fragment] subclass.
 */
class FeedBackFragment : MvpAppCompatFragment(), ViewFeedBack {

    @InjectPresenter
    lateinit var presenter: PresenterFeedBack

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_back, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mask = MaskImpl(PredefinedSlots.RUS_PHONE_NUMBER, true)
        val watcher = MaskFormatWatcher(mask)
        watcher.installOn(edt_tel_num_feedback)

        var number = ""
        edt_tel_num_feedback.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                number = s.toString().replace("(", "")
                    .replace(")", "").replace("-", "").replace(" ", "")
            }
        })

        btn_send_message.setOnClickListener {
            val secondName = edt_secondname_feedback.text.toString()
            val firstName = edt_firstname_feedback.text.toString()
            val report = edt_problem_feedback.text.toString()
            if (secondName.isEmpty() ||
                firstName.isEmpty() ||
                number.isEmpty() ||
                report.isEmpty()
            ) {
                MaterialDialog(requireContext()).show {
                    title(R.string.error)
                    message(text = "Заполните поля")
                    icon(R.drawable.ic_uncheck)
                }
            } else
                presenter.sendReport(
                    secondName = secondName,
                    firstName = firstName,
                    telNumber = number,
                    report = report
                )
        }


    }

    override fun load() {
        edt_firstname_feedback.isEnabled = false
        edt_secondname_feedback.isEnabled = false
        edt_tel_num_feedback.isEnabled = false
        edt_problem_feedback.isEnabled = false
        cpv_sign_in_loader.visibility = View.VISIBLE
    }

    override fun errorLoad(textError: String?) {
        MaterialDialog(requireContext()).show {
            title(R.string.error)
            message(text = textError)
            icon(R.drawable.ic_uncheck)
        }
        edt_firstname_feedback.isEnabled = true
        edt_secondname_feedback.isEnabled = true
        edt_tel_num_feedback.isEnabled = true
        edt_problem_feedback.isEnabled = true
        cpv_sign_in_loader.visibility = View.GONE
    }

    override fun successfulLoad() {
        MaterialDialog(requireContext()).show {
            title(R.string.successful)
            message(R.string.send_success)
            icon(R.drawable.ic_check)
        }
        edt_firstname_feedback.isEnabled = true
        edt_secondname_feedback.isEnabled = true
        edt_tel_num_feedback.isEnabled = true
        edt_problem_feedback.isEnabled = true
        cpv_sign_in_loader.visibility = View.GONE
    }
}
