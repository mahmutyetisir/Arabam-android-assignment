package com.myetisir.core.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.myetisir.core.ui.viewmodel.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM

    abstract val binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onViewCreated(savedInstanceState)
        observeViewModel()
    }

    abstract fun onViewCreated(savedInstanceState: Bundle?)

    abstract fun observeViewModel()
}