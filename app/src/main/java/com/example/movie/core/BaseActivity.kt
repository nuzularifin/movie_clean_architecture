package com.example.movie.core

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun setLayout(): View

    abstract fun initializeView()

    open fun observeView() {}

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initializeView()
        setContentView(setLayout())
        observeView()
    }

}