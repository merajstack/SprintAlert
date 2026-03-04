package com.sprintalert

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sprintalert.service.GPSTrackingService
import com.sprintalert.utils.AlertManager
import com.sprintalert.utils.formatTime
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: SprintViewModel
    private var isHistoryVisible = false

    private lateinit var timerText: TextView
    private lateinit var distanceText: TextView
    private lateinit var gpsStatusText: TextView
    private lateinit var targetInput: EditText
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var historyButton: Button
    private lateinit var settingsButton: Button
    private lateinit var recordsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewModel = ViewModelProvider(this).get(SprintViewModel::class.java)
        
        initializeViews()
        setupListeners()
        observeState()
        requestPermissions()
        startGPSTrackingService()
    }

    private fun initializeViews() {
        timerText = findViewById(R.id.timer_text)
        distanceText = findViewById(R.id.distance_text)
        gpsStatusText = findViewById(R.id.gps_status)
        targetInput = findViewById(R.id.target_distance_input)
        startButton = findViewById(R.id.start_button)
        stopButton = findViewById(R.id.stop_button)
        resetButton = findViewById(R.id.reset_button)
        historyButton = findViewById(R.id.history_button)
        settingsButton = findViewById(R.id.settings_button)
        recordsList = findViewById(R.id.records_list)
    }

    private fun setupListeners() {
        startButton.setOnClickListener {
            val distance = targetInput.text.toString().toFloatOrNull() ?: 100f
            viewModel.setTargetDistance(distance)
            viewModel.startSprint()
        }

        stopButton.setOnClickListener {
            viewModel.stopSprint()
        }

        resetButton.setOnClickListener {
            viewModel.resetSprint()
        }

        historyButton.setOnClickListener {
            isHistoryVisible = !isHistoryVisible
            updateHistoryVisibility()
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                timerText.text = formatTime(state.currentTime)
                distanceText.text = String.format("%.2f m", state.currentDistance)
                gpsStatusText.text = state.gpsStatus
                targetInput.setText(state.targetDistance.toInt().toString())

                startButton.isEnabled = !state.isRunning
                stopButton.isEnabled = state.isRunning
                
                if (state.currentDistance >= state.targetDistance && !state.isRunning && state.currentDistance > 0) {
                    AlertManager.playAlertSound(this@MainActivity)
                    AlertManager.vibrateAlert(this@MainActivity)
                }

                if (state.history.isNotEmpty() && isHistoryVisible) {
                    val adapter = RecordAdapter(
                        state.history,
                        onDelete = { viewModel.deleteRecord(it) }
                    )
                    recordsList.adapter = adapter
                }
            }
        }
    }

    private fun updateHistoryVisibility() {
        recordsList.visibility = if (isHistoryVisible) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun requestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.VIBRATE
        )
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE)
        }

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startGPSTrackingService() {
        val intent = Intent(this, GPSTrackingService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Permissions handled
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}
