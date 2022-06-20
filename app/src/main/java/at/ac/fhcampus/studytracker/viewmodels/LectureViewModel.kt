package at.ac.fhcampus.studytracker.viewmodels


import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.repositories.LectureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class LectureViewModel(
    private val repository: LectureRepository,
    private val lectureId: Long? = null
) : ViewModel() {

    private var _lectures = MutableStateFlow<List<Lecture>>(emptyList())
    val lectures = _lectures.asStateFlow()

    val lecture = repository.filterLecture(lectureId = lectureId)

    init {
        viewModelScope.launch(Dispatchers.IO) { // launch a coroutine in IO thread
            repository.getAllLectures().distinctUntilChanged()
                .collect{ listOfLectures ->
                    if(listOfLectures.isNullOrEmpty()){
                        Log.d("LectureViewModel", "Empty note list")
                        _lectures.value = emptyList()
                    } else {
                        _lectures.value = listOfLectures
                    }
                }
        }

        /*
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllLectures().collect{ listOfLectures ->
                if(listOfLectures.isNullOrEmpty()){
                    //
                    Log.d("LectureViewModel", "No Lectures")
                } else {
                    _lectures.value = listOfLectures
                }
            }
        }*/
    }


    fun addLecture(lecture: Lecture){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLecture(lecture = lecture)
        }
    }

    fun removeLecture(lecture: Lecture){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLecture(lecture)
        }
    }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }

    fun editLecture(lecture: Lecture){
        viewModelScope.launch(Dispatchers.IO) {
            repository.editLecture(lecture = lecture)
        }
    }

    fun filterLecture(lectureId: Long?) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.filterLecture(lectureId = lectureId)
        }
    }
}

