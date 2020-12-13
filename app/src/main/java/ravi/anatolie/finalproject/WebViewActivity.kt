package ravi.anatolie.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ravi.anatolie.finalproject.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    // region properties of the DetailsActivity
    private lateinit var binding: ActivityWebViewBinding // create our binding

    // endregion

    // region WebViewActivity Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        //init our view binding
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // start the webview with the stored url in the android intent bundle
        val url =  intent.getStringExtra(getString(R.string.url_key))

        binding.webViewGitHub.settings.javaScriptEnabled = true
        binding.webViewGitHub.settings.loadWithOverviewMode = true
        binding.webViewGitHub.settings.useWideViewPort = true



        binding.webViewGitHub.loadUrl(url!!)
    }

    // endregion
}