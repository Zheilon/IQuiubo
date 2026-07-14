package com.zhei.hikibo.core.common
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId


/**
 * Obtiene la activity asociada al Context actual, si existe.
 * */
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


/**
 * Convierte un LocalDate a TimeStamp
 * */
fun LocalDate.toTimeStamp(): Timestamp {
    val instant = this.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return Timestamp(instant.epochSecond, instant.nano)
}


/**
 * Convierte un TimeStamp a LocalDate
 * */
fun Timestamp.toLocalDate(): LocalDate =
    this.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()


/**
 * Convierte un Instant en Timestamp
 * */
fun Instant.toTimestamp(): Timestamp =
    Timestamp(this.epochSecond, this.nano)


/**
 * Convierte un Timestamp a Instant
 * */
fun Timestamp.toInstant(): Instant =
    Instant.ofEpochSecond(this.seconds, this.nanoseconds.toLong())


/**
 * Convierte un fecha de formato ISO-8601 (2003-12-02) a LocalDate
 * */
fun String.parseToLocalDate(): LocalDate =
    LocalDate.parse(this)


/**
 * Calcula la edad en años a partir de la fecha actual
 * */
fun LocalDate.calculateAge(today: LocalDate = LocalDate.now()): Int =
    Period.between(this, today).years