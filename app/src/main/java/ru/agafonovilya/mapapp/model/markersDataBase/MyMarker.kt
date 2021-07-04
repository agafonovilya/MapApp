package ru.agafonovilya.mapapp.model.markersDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "markers_table")
class MyMarker(@PrimaryKey(autoGenerate = true) val id: Long = 0,
               @ColumnInfo(name = "title") var title: String = "no title",
               @ColumnInfo(name = "latitude") val latitude: Double,
               @ColumnInfo(name = "longitude") val longitude: Double,
               @ColumnInfo(name = "description") var description: String = "no description") : Serializable