package at.ac.fhcampus.studytracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import at.ac.fhcampus.studytracker.models.Lecture
import kotlinx.coroutines.flow.Flow


@Dao
interface LectureDao {
    @Insert
    suspend fun addLecture(lecture: Lecture)

    @Update
    suspend fun editLecture(lecture: Lecture)

    @Delete
    suspend fun deleteLecture(lecture: Lecture)

    @Query("SELECT * from lectures")
    fun getLectures(): Flow<List<Lecture>>

    @Query("DELETE FROM lectures")
    suspend fun deleteAll()

    @Query("SELECT * from lectures where id=:id")
    fun getLectureById(id: Long?): LiveData<Lecture>
}