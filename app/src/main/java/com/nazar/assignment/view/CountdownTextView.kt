package com.nazar.assignment.view

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.nazar.assignment.R
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CountdownTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private var timer: CountDownTimer? = null

    fun startCountdown(countToMillis: Long, @StringRes endText: Int) {
        cancelCountdown()
        timer = object : CountDownTimer(
            countToMillis - System.currentTimeMillis(),
            COUNTDOWN_DELAY
        ) {
            override fun onTick(millisUntilFinished: Long) {
                setTimeText(millisUntilFinished)
            }

            override fun onFinish() {
                text = resources.getText(endText)
            }
        }
        timer?.start()
    }

    fun cancelCountdown() {
        timer?.cancel()
    }

    private fun setTimeText(millis: Long) {
        val duration = millis.toDuration(DurationUnit.MILLISECONDS)
        duration.toComponents { days, hours, minutes, seconds, _ ->
            text = if (days > 0) {
                resources.getString(R.string.event_days_countdown, days, hours, minutes, seconds)
            } else {
                resources.getString(R.string.event_hours_countdown, hours, minutes, seconds)
            }
        }
    }

    companion object {
        private const val COUNTDOWN_DELAY = 1000L
    }
}
