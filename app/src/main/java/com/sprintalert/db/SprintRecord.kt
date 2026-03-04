package com.sprintalert.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sprint_records")
data class SprintRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long,
    val distance: Float,
    val timeInSeconds: Float,
    val speed: Float = 0f
)
