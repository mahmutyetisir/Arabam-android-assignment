package com.myetisir.core.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.myetisir.core.ui.dialog.InfoDialog
import com.myetisir.core.ui.viewmodel.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel>(view: Int) : Fragment(view) {

    abstract val viewModel: VM
    abstract val binding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDidLoad(savedInstanceState)
        observeViewModel()
    }

    abstract fun viewDidLoad(savedInstanceState: Bundle?)

    abstract fun observeViewModel()

    fun showInfoPopup(message: String?, cancellable: Boolean = true, callback: () -> Unit = {}) {
        requireActivity().apply {
            InfoDialog(this, message, cancellable, callback = callback).show()
        }
    }

    open fun onBackPressed() {}

    companion object {
        const val CLICK_TIMEOUT = 300L
    }
}