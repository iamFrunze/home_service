package com.byfrunze.homeservice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.byfrunze.homeservice.R
import kotlinx.android.synthetic.main.fragment_service.*
import moxy.MvpAppCompatFragment


class ServiceFragment : MvpAppCompatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_service_1.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.nav_online_reg)
        }
        btn_service_2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.nav_online_reg)
        }
        btn_service_3.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.nav_online_reg)
        }
        btn_service_4.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.nav_online_reg)
        }
    }

}
