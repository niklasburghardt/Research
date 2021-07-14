package com.example.research.Dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment : DialogFragment(){
    private lateinit var listener: DatePickerDialog.OnDateSetListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is DatePickerDialog.OnDateSetListener){
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            listener, year, month, day
        )
    }
    companion object{
        val TAG = DatePickerFragment::class.simpleName
    }
}