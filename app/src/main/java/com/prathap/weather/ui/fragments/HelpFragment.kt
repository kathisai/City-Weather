package com.prathap.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prathap.weather.R
import kotlinx.android.synthetic.main.fragment_help.*

/**
 * simple fragment to show webView with HTML code in it.
 */
class HelpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val html: String = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<h1>Help</h1>\n" +
                "<p > ➡ Add you favirote city to Home by click on + </p>\n" +
                "<p > ➡ Drag marker to desired location and click on confirm location button</p>\n" +
                "<p > ➡ Click on city to see more weather insights</p>\n" +
                "<p > ➡ Swipe Left to delete Item</p>\n" +
                "</body>\n" +
                "</html>"
        wvHelp.loadData(html, "text/html", null)
        wvHelp.webViewClient = WebViewClient()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }


}