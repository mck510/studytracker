package at.ac.fhcampus.studytracker.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.ac.fhcampus.studytracker.repositories.LectureRepository

@Suppress("UNCHECKED_CAST")
class LectureViewModelFactory(
    private val repository: LectureRepository,
    private val lectureId: Long?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LectureViewModel::class.java)) {
            return LectureViewModel(repository = repository, lectureId = lectureId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}