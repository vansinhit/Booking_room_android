package com.example.booking_room.utils

import android.app.ProgressDialog
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import com.example.booking_room.R
import java.lang.Exception
import java.net.URL

class ReceiveImage : AsyncTask<String, Void, Bitmap>() {
    lateinit var progressDialog: ProgressDialog;

    override fun doInBackground(vararg params: String?): Bitmap {
        var bitmap: Bitmap

        try {
            val inputStream = URL(params[0]).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.airbnb_logo)
        }

        return bitmap
    }

    override fun onPostExecute(result: Bitmap?) {
        this.cancel(true);
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }
}