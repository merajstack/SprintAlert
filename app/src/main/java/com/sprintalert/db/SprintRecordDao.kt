package com.sprintalert.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SprintRecordDao {
    @Insert
    suspend fun insert(record: SprintRecord)

    @Delete
    suspend fun delete(record: SprintRecord)

    @Query("SELECT * FROM sprint_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<SprintRecord>>

    @Query("SELECT * FROM sprint_records WHERE distance = :distance ORDER BY timeInSeconds ASC LIMIT 1")
    suspend fun getBestTimeForDistance(distance: Float): SprintRecord?

    @Query("SELECT DISTINCT distance FROM sprint_records ORDER BY distance ASC")
    fun getDistances(): Flow<List<Float>>

    @Query("DELETE FROM sprint_records")
    suspend fun deleteAll()
}
