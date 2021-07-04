package ru.agafonovilya.mapapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.agafonovilya.mapapp.model.markersDataBase.MarkersRoomDatabase
import ru.agafonovilya.mapapp.repository.MarkersRepository

class App: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { MarkersRoomDatabase.getDatabase(this) }
    val repository by lazy { MarkersRepository(database.markersDao()) }
}