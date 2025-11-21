package com.mff.githubapp.ui

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

/**
 * Opens browser with [url].
 */
fun Context.openBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    startActivity(intent)
}