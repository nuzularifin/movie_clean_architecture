package com.example.movie.core.extentions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = this

fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}

fun visibleMultipleViews(vararg views: View) {
    views.forEach {
        it.visible()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.visible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.gone() {
    visibility = View.GONE
}

fun goneMultipleViews(vararg views: View) {
    views.forEach {
        it.gone()
    }
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
