package at.ac.fhcampus.studytracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "lectures")
data class Lecture(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "lecture_name")
    var name: String,
    @ColumnInfo(name = "semester")
    var semester: String,
    @ColumnInfo(name = "lecturer")
    var lecturer: String,
    @ColumnInfo(name = "ects")
    var ects: Float,
    @ColumnInfo(name = "sws")
    var sws: Float,
    @ColumnInfo(name = "link")
    var link: String,
    @ColumnInfo(name = "learning_hours_todo")
    var todo: Float,
    @ColumnInfo(name = "learning_hours_done")
    var done: Float
)
