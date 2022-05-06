package com.nazar.assignment.extensions

import android.content.res.Resources

val Int.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
