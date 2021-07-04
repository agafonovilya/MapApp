package ru.agafonovilya.mapapp.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.agafonovilya.mapapp.model.markersDataBase.MyMarker
import ru.agafonovilya.mapapp.model.markersDataBase.MarkersDao

class MarkersRepository(private val markersDao: MarkersDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allMarkers: Flow<List<MyMarker>> = markersDao.getAllMarkers()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMarker(myMarker: MyMarker){
        markersDao.insertMarker(myMarker)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateMarker(myMarker: MyMarker){
        markersDao.updateMarker(myMarker)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteMarker(myMarker: MyMarker){
        markersDao.deleteMarker(myMarker)
    }
}