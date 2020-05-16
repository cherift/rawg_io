package com.example.rawg_youtubemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class YoutubeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        val webWiew : WebView = findViewById(R.id.webview)
        val baseUrl = "https://www.youtube.com/watch?v="
        val message = intent.getStringExtra("MESSAGE")
        webWiew.loadUrl(baseUrl + message)

        //close the activity after opening the video in browser or navigators
        finish()
    }
}
