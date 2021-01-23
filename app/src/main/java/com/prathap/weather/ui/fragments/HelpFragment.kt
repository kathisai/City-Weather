package com.prathap.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.prathap.weather.R
import com.prathap.weather.di.Injectable
import com.prathap.weather.ui.HomeViewModel
import kotlinx.android.synthetic.main.fragment_help.*
import javax.inject.Inject


class HelpFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wvHelp.loadUrl("https://google.com")
        // Enable Javascript
        val webSettings: WebSettings = wvHelp.settings
        webSettings.javaScriptEnabled = true

        wvHelp.webViewClient = WebViewClient()

    }


}