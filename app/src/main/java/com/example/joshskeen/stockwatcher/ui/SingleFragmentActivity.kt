package com.example.joshskeen.stockwatcher.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

import com.example.joshskeen.stockwatcher.R
import com.example.joshskeen.stockwatcher.databinding.ActivitySingleFragmentBinding


abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment
    private lateinit var viewDataBinding: ActivitySingleFragmentBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_fragment)
        savedInstanceState ?: supportFragmentManager.addFragment()
    }

    private fun FragmentManager.addFragment() =
            beginTransaction().add(viewDataBinding.fragmentContainer.id, createFragment()).commit()

}
