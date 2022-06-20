package at.ac.fhcampus.studytracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.models.Settings
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingsDao {
    @Insert
    fun addSettings(settings: Settings)

    @Update
    suspend fun editSettings(settings: Settings)

    @Delete
    suspend fun deleteSettings(settings: Settings)

    @Query("SELECT * from settings")
    fun getSettings(): Flow<List<Settings>>

    @Query("DELETE FROM settings")
    suspend fun deleteAll()

    @Query("SELECT * from settings where id=:id")
    fun getSettingById(id: Long?): LiveData<Settings>
}