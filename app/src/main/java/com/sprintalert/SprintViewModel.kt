package com.sprintalert

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sprintalert.db.SprintAlertDatabase
import com.sprintalert.db.SprintRecord
import com.sprintalert.utils.DistanceCalculator
import com.sprintalert.utils.GPSManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

data class SprintState(
    val isRunning: Boolean = false,
    val currentTime: Float = 0f,
    val currentDistance: Float = 0f,
    val targetDistance: Float = 100f,
    val gpsStatus: String = "Initializing GPS...",
    val bestTime: Float? = null,
    val history: List<SprintRecord> = emptyList()
)

class SprintViewModel(application: Application) : AndroidViewModel(application) {
    private val database = SprintAlertDatabase.getInstance(application)
    private val dao = database.sprintRecordDao()
    private val gpsManager = GPSManager(
        application,
        LocationServices.getFusedLocationProviderClient(application)
    )

    private val _state = MutableStateFlow(SprintState())
    val state: StateFlow<SprintState> = _state.asStateFlow()

    private val locationHistory = mutableListOf<Location>()
    private var startTime = 0L
    private var timer: Timer? = null
    private var lastLocation: Location? = null

    init {
        startGPSTracking()
        loadHistory()
    }

    private fun startGPSTracking() {
        viewModelScope.launch {
            gpsManager.getLocationUpdates().collect { location ->
                lastLocation = location
                if (_state.value.isRunning) {
                    locationHistory.add(location)
                    updateDistance()
                }
                _state.value = _state.value.copy(gpsStatus = "GPS Ready")
            }
        }
    }

    private fun updateDistance() {
        if (locationHistory.size < 2) return
        val distance = DistanceCalculator.getTravelDistance(locationHistory)
        _state.value = _state.value.copy(currentDistance = distance)

        if (distance >= _state.value.targetDistance && _state.value.isRunning) {
            stopSprint()
            onSprintComplete()
        }
    }

    fun startSprint() {
        if (_state.value.isRunning) return
        
        locationHistory.clear()
        _state.value = _state.value.copy(
            isRunning = true,
            currentTime = 0f,
            currentDistance = 0f
        )
        startTime = System.currentTimeMillis()
        startTimer()
    }

    fun stopSprint() {
        if (!_state.value.isRunning) return
        timer?.cancel()
        _state.value = _state.value.copy(isRunning = false)
    }

    fun resetSprint() {
        timer?.cancel()
        locationHistory.clear()
        _state.value = _state.value.copy(
            isRunning = false,
            currentTime = 0f,
            currentDistance = 0f
        )
    }

    private fun startTimer() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (_state.value.isRunning) {
                    val elapsed = (System.currentTimeMillis() - startTime) / 1000f
                    _state.value = _state.value.copy(currentTime = elapsed)
                }
            }
        }, 100, 100)
    }

    fun setTargetDistance(distance: Float) {
        _state.value = _state.value.copy(targetDistance = distance)
    }

    private fun onSprintComplete() {
        viewModelScope.launch {
            val record = SprintRecord(
                date = System.currentTimeMillis(),
                distance = _state.value.currentDistance,
                timeInSeconds = _state.value.currentTime
            )
            dao.insert(record)
            loadBestTime()
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            dao.getAllRecords().collect { records ->
                _state.value = _state.value.copy(history = records)
            }
        }
    }

    private fun loadBestTime() {
        viewModelScope.launch {
            val best = dao.getBestTimeForDistance(_state.value.targetDistance)
            _state.value = _state.value.copy(bestTime = best?.timeInSeconds)
        }
    }

    fun deleteRecord(record: SprintRecord) {
        viewModelScope.launch {
            dao.delete(record)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
