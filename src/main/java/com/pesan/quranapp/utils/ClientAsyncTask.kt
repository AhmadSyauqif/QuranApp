package com.pesan.quranapp.utils

import android.os.AsyncTask
import com.pesan.quranapp.fragment.JadwalShalatFragment
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ClientAsyncTask(
    private val mContext: JadwalShalatFragment,
    postExecuteListener: OnPostExecuteListener

) : AsyncTask<String, String, String>() {
    val CONNECTON_TIMEOUT_MILLISECONDS = 60000
    private val mPostExecuteListener: OnPostExecuteListener = postExecuteListener

    interface OnPostExecuteListener {
        fun onPostExecute(result: String)
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        mPostExecuteListener.onPostExecute(result)
    }

    override fun doInBackground(vararg urls: String?): String {
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(urls[0])

            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = CONNECTON_TIMEOUT_MILLISECONDS
            urlConnection.readTimeout = CONNECTON_TIMEOUT_MILLISECONDS

            return streamToString(urlConnection.inputStream)

        } catch (ex: Exception) {

        } finally {
            urlConnection?.disconnect()
        }

        return ""
    }

    private fun streamToString(inputStream: InputStream): String { //private ga nih

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (true)
            inputStream.close()
        } catch (ex: Exception) {

        }

        return result
    }
}