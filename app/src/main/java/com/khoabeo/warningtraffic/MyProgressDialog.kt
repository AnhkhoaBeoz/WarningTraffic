package com.khoabeo.warningtraffic

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView


class MyProgressDialog(private val progressBar: ProgressBar) {

    fun showProgressDialog() {
        progressBar.visibility = View.VISIBLE

    }

    fun dismissProgressDialog() {
        progressBar.visibility = View.GONE
    }
}

