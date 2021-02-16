package com.dormantech.bitcoin_price_check

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.*
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val apiURL = "https://api.coindesk.com/v1/bpi/currentprice.json"

    var okHTTPClient: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeHTTPRequest()

        refreshRequestButton.setOnClickListener {
            makeHTTPRequest()
        }
    }

    private fun makeHTTPRequest() {

        val request: Request = Request.Builder().url(apiURL).build()

        okHTTPClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {

                val jsonResponse = response?.body?.string()

                val usdValue = (JSONObject(jsonResponse).getJSONObject("bpi").getJSONObject("USD")["rate"] as String)

                runOnUiThread {
                    currentValueView.text = "USD $$usdValue"
                }
            }



        })

    }
}