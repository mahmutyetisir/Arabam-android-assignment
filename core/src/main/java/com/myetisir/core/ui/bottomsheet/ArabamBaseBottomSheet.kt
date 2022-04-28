package com.myetisir.core.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myetisir.core.R

abstract class ArabamBaseBottomSheet<T : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: T? = null
    val binding: T get() = _binding!!

    abstract fun isDialogCancellable(): Boolean

    abstract fun getViewBinding(): T

    open fun viewLoaded() {}

    override fun getTheme(): Int = R.style.ArabamBottomSheetStyle

    open fun setWindow() {
        dialog?.setCanceledOnTouchOutside(isDialogCancellable())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWindow()
        viewLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}