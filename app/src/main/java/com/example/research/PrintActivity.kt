package com.example.research

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintManager
import androidx.annotation.RequiresApi

class PrintActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(PrintManager::class.java)?.let {
                val jobName = "${getString(R.string.app_name)} Document"
                it.print(
                    jobName, PdfPrintAdapter(this), null
                )
            }
        }
    }
}