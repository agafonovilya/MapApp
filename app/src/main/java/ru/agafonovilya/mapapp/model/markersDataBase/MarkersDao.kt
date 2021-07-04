package ru.agafonovilya.mapapp.model.markersDataBase

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MarkersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(myMarker: MyMarker)

    @Update
    suspend fun updateMarker(myMarker: MyMarker)

    @Delete
    suspend fun deleteMarker(myMarker: MyMarker)

    @Query("SELECT * FROM markers_table ORDER BY id ASC")
    fun getAllMarkers(): Flow<List<MyMarker>>
}