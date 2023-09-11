package net.datadoghq.sls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumMonitor
import com.datadog.android.webview.DatadogEventBridge
import com.example.joongomarketwebview.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clientToken = "pub46627c899474f0f1660c6de1d8fbcbcc"
        val applicationId = "6f8dfdc0-07f5-412d-a01d-9af8a2629157"

        val environmentName = "stg"
        val appVariantName = "android_webview_test"
        val host = "https://sls.datadoghq.net"
        val configuration = Configuration.Builder(
            logsEnabled = true,
            tracesEnabled = true,
            crashReportsEnabled = true,
            rumEnabled = true,
        )
            .sampleRumSessions(100.0f)
            .trackInteractions()
            .useSite(DatadogSite.US1)
            .setWebViewTrackingHosts(listOf(host))
            .build()
        val credentials = Credentials(clientToken,environmentName,appVariantName,applicationId)
        Datadog.initialize(this, credentials, configuration, TrackingConsent.GRANTED)
        GlobalRum.registerIfAbsent(RumMonitor.Builder().build())

        setContentView(R.layout.activity_main)
        val myWeb = findViewById<WebView>(R.id.MyWebView)
        myWeb.webViewClient = WebViewClient()
        DatadogEventBridge.setup(myWeb)

        myWeb.apply {
            loadUrl("https://sls.datadoghq.net")
            settings.javaScriptEnabled = true
        }
    }
}