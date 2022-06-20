package at.ac.fhcampus.studytracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow
import java.util.*

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "goal")
    var goal: Float,
    @ColumnInfo(name = "date")
    var date: String
)
