package at.ac.fhcampus.studytracker.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.ac.fhcampus.studytracker.repositories.LectureRepository
import at.ac.fhcampus.studytracker.repositories.SettingsRepository

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(
    private val repository: SettingsRepository,
    private val settingsId: Long?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repository = repository, settingsId = settingsId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}