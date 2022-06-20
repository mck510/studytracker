package at.ac.fhcampus.studytracker.repositories

import androidx.lifecycle.LiveData
import at.ac.fhcampus.studytracker.db.LectureDao
import at.ac.fhcampus.studytracker.models.Lecture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

class LectureRepository(private val dao: LectureDao) {

    suspend fun addLecture(lecture: Lecture) = dao.addLecture(lecture = lecture)

    suspend fun editLecture(lecture: Lecture) = dao.editLecture(lecture = lecture)

    suspend fun deleteLecture(lecture: Lecture) = dao.deleteLecture(lecture = lecture)

    suspend fun deleteAll() = dao.deleteAll()

    fun getAllLectures(): Flow<List<Lecture>> = dao.getLectures()

    fun filterLecture(lectureId: Long?): LiveData<Lecture> = dao.getLectureById(lectureId)

}