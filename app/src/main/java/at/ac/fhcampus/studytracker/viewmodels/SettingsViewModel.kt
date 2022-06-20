package at.ac.fhcampus.studytracker.viewmodels


import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.models.Settings
import at.ac.fhcampus.studytracker.repositories.LectureRepository
import at.ac.fhcampus.studytracker.repositories.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val repository: SettingsRepository,
    private val settingsId: Long? = null
) : ViewModel() {


    val settings = repository.filterSettings(1)


    fun addSettings(settings: Settings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSettings(settings = settings)
        }
    }


    fun editSettings(settings: Settings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editSettings(settings = settings)
        }
    }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }

    fun filterLecture(lectureId: Long?) {

        viewModelScope.launch(Dispatchers.IO) {


            repository.filterSettings(settingsId = settingsId)
        }
    }
}

