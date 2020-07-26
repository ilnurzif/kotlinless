package com.geekless.kotlianappless.frameworks.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(this)
fun Date.formatDate(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(this)