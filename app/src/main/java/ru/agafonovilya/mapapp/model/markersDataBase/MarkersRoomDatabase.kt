package ru.agafonovilya.mapapp.model.markersDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyMarker::class], version = 1, exportSchema = false)
abstract class MarkersRoomDatabase : RoomDatabase() {

    abstract fun markersDao(): MarkersDao

    //Singleton prevents multiple instances of database opening at the same time.
    companion object {
        @Volatile
        private var INSTANCE: MarkersRoomDatabase? = null

        fun getDatabase(context: Context): MarkersRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarkersRoomDatabase::class.java,
                    "markers_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}