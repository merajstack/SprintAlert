package com.sprintalert.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.media.MediaPlayer
import androidx.core.content.ContextCompat
import java.io.IOException

object AlertManager {
    private var mediaPlayer: MediaPlayer? = null

    fun playAlertSound(context: Context) {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, android.net.Uri.parse("android.resource://${context.packageName}/raw/alert_sound"))
                prepare()
                setVolume(1.0f, 1.0f)
                start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun vibrateAlert(context: Context) {
        val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java) ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val pattern = longArrayOf(0, 500, 200, 500, 200, 500)
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 500, 200, 500, 200, 500), -1)
        }
    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
