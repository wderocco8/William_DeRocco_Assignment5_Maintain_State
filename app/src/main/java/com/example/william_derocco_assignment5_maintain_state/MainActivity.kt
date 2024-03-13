package com.example.william_derocco_assignment5_maintain_state

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // initialize fields
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // obtain elements
        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        val button: Button = findViewById(R.id.button)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Restore state
        loadStateFromPreferences()

        // add listener to load new images
        button.setOnClickListener {
            // Load random image
            loadImageRandom()
        }

        // add listener for text changes
        editText.addTextChangedListener(object : TextWatcher {
            // do nothing for before/on text change
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Save text
                val editor = sharedPreferences.edit()
                editor.putString("text", s.toString())
                editor.apply()
            }
        })
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//        // Save state
//        val editor = sharedPreferences.edit()
//        editor.putString("text", editText.text.toString())
//        editor.apply()
//    }

    private fun loadImageRandom() {
        // Load random image from drawables
        val images = arrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.placeholder_image)
        val randomIndex = Random.nextInt(images.size)
        imageView.setImageResource(images[randomIndex])

        // Save image index
        val editor = sharedPreferences.edit()
        editor.putInt("image", images[randomIndex])
        editor.apply()
    }

    private fun loadStateFromPreferences() {
        // Load image from SharedPreferences
        val savedImage = sharedPreferences.getInt("image", -1)
        if (savedImage != -1) {
            imageView.setImageResource(savedImage)
        }

        // Load text from SharedPreferences
        val savedText = sharedPreferences.getString("text", "")
        if (savedText != "") {
            editText.setText(savedText)
        }
    }
}