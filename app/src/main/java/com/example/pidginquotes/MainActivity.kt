package com.example.pidginquotes

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.pidginquotes.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url: String = "https://meme-api.com/gimme"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnmeme.setOnClickListener {
            getmeme()
        }


        getmeme()
// ...

// Instantiate the RequestQueue.

    }

    private fun getmeme() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Wait While We Fetch Meme")
        progressDialog.show()

        val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.e("response", "getmeme:  " + response.toString())
                var resoneObject = JSONObject(response)
                resoneObject.get("title")
                resoneObject.get("url")
                resoneObject.get("postLink")
                resoneObject.get("author")

                binding.memeTitle.text = resoneObject.getString("title")
                binding.memeAuthor.text = resoneObject.getString("author")

                Glide.with(this).load(resoneObject.get("url")).into(binding.memeImage)
                progressDialog.dismiss()


                // Display the first 500 characters of the response string.
            },
            { error ->
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "${error.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()

            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)


    }
}