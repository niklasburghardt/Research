package com.example.research

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.pdf.PrintedPdfDocument
import androidx.annotation.RequiresApi
import java.io.FileOutputStream
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.KITKAT)
class PdfPrintAdapter(private val context: Context) : PrintDocumentAdapter() {
    private var numPages = 0
    private var pdf: PrintedPdfDocument? = null

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        disposePdf()
        pdf = PrintedPdfDocument(context, newAttributes)
        if (cancellationSignal != null) {
            if(cancellationSignal.isCanceled){
                if (callback != null) {
                    callback.onLayoutCancelled()
                }
                disposePdf()
                return
            }
        }
        numPages = computePageCount(newAttributes)
        if(numPages > 0){
            val info = PrintDocumentInfo.Builder("projektname_quellen.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(numPages)
                .build()

        }
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        pdf?.let { pdf->
            for(i in 0 until numPages){
                if (cancellationSignal != null) {
                    if(cancellationSignal.isCanceled){
                        if (callback != null) {
                            callback.onWriteCancelled()
                        }
                        disposePdf()
                        return
                    }
                }
                val page = pdf.startPage(i)
                drawPage(page)
                pdf.finishPage(page)
            }
            try{
                if (destination != null) {
                    FileOutputStream(
                        destination.fileDescriptor
                    ).let { stream ->
                        pdf.writeTo(stream)
                    }
                }
            }catch (e: IOException){
                if (callback != null) {
                    callback.onWriteFailed(e.toString())
                }
                return
            }
            try {
                if (destination != null) {
                    destination.close()
                }
            }catch (e: IOException){
                if (callback != null) {
                    callback.onWriteFailed(e.toString())
                }
                return
            }
        }
        if (callback != null) {
            callback.onWriteFinished(pages)
        }
    }

    override fun onFinish() {
        disposePdf()
    }

    private fun computePageCount(printAttributes: PrintAttributes): Int{
        val size = printAttributes.mediaSize
        return if(size==null || !size.isPortrait) 2 else 1
    }

    private fun drawPage(page: PdfDocument.Page){
        val nr = page.info.pageNumber.toFloat()
        val w = page.canvas.width.toFloat()
        val h = page.canvas.height.toFloat()
        val cx = w / 2f
        val cy = h / 2f
        val paint = Paint()
        paint.strokeWidth = 3f
        paint.color = Color.BLUE
        page.canvas.drawText("Hello", 1, 1, 1f, 1f, paint)
    }
    private fun disposePdf(){
        pdf?.close()
        pdf = null
    }
}