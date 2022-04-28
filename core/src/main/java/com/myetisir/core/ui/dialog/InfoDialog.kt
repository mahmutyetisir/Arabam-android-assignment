package com.myetisir.core.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.myetisir.core.R
import com.myetisir.core.databinding.DialogInfoBinding

class InfoDialog(
    context: Context,
    private val message: String? = null,
    private val cancellable: Boolean = false,
    private val callback: () -> Unit
) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogInfoBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        binding.dialogInfoTxtMessage.text = message ?: context.getString(R.string.app_name)
        binding.dialogButton.setOnClickListener {
            dismiss()
            callback()
        }
        setCancelable(cancellable)
    }
}