package at.ac.fhcampus.studytracker.repositories

import androidx.lifecycle.LiveData
import at.ac.fhcampus.studytracker.db.LectureDao
import at.ac.fhcampus.studytracker.db.SettingsDao
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.models.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

class SettingsRepository(private val dao: SettingsDao) {

    fun addSettings(settings: Settings) = dao.addSettings(settings = settings)

    suspend fun editSettings(settings: Settings) = dao.editSettings(settings = settings)

    fun filterSettings(settingsId: Long?): LiveData<Settings> = dao.getSettingById(settingsId)

    suspend fun deleteAll() = dao.deleteAll()

}