package com.arabam.android.assigment.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.arabam.android.assigment.R
import com.arabam.android.assigment.databinding.ActivityMainBinding
import com.myetisir.core.common.viewBinding
import com.myetisir.core.ui.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
    override val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        supportFragmentManager.findFragmentById(R.id.mainNavFragment) as NavHostFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.mainNavFragment).navigateUp() || super.onSupportNavigateUp()
    }

    override fun observeViewModel() {
    }
}