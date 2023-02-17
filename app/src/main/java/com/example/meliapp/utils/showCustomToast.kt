package com.example.meliapp.utils

import android.app.Activity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.meliapp.R


fun Toast.showCustomToast(message: String, activity: Activity) {
    try {
        val layout = activity.layoutInflater.inflate(
            R.layout.custom_toast_layout_relative,
            activity.findViewById(R.id.toast_container)
        )

        // set the text of the TextView of the message
        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message

        layout.findViewById<ImageView>(R.id.iv_close).setOnClickListener {
            cancel()
        }

        // use the application extension function
        this.apply {
            setGravity(Gravity.BOTTOM, 0, 0)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}