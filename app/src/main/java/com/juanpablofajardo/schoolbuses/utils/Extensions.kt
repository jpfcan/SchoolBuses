package com.juanpablofajardo.schoolbuses.utils

import java.util.concurrent.TimeUnit


/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 */
fun Long.milisecondsGetHours() = TimeUnit.MILLISECONDS.toHours(this)

fun Long.milisecondsGetMinutes() = TimeUnit.MILLISECONDS.toMinutes(this)

fun Long.milisecondsToSeconds() = TimeUnit.MILLISECONDS.toSeconds(this)

fun Long.miliSecondGetSeconds() = this / 1000 % 60