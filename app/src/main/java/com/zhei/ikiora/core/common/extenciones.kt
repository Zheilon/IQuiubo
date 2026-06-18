package com.zhei.ikiora.core.common
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}