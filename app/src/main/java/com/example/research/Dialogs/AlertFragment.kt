package com.example.research.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

class AlertFragment : DialogFragment() {
    private lateinit var listener: DialogInterface.OnClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is DialogInterface.OnClickListener) {
            //TODO: Bedingung wird nicht erfüllt und listener wird nicht initalisiert
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete")
        builder.setMessage("Do you really want to delete this source")
        builder.setCancelable(false)
        builder.setPositiveButton("close", listener)
        return builder.create()
    }
    companion object {
        val TAG = AlertFragment::class.simpleName
    }
}