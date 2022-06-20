package at.ac.fhcampus.studytracker.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.navigation.StudyTrackerScreens
import at.ac.fhcampus.studytracker.repositories.LectureRepository
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModel
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditSingleLectureScreen(
    lectureId: Long?,
    navController: NavController,
    repository: LectureRepository
) {

    val lectureViewModel: LectureViewModel = viewModel(
        factory = LectureViewModelFactory(repository = repository, lectureId = lectureId)
    )
    val lecture by lectureViewModel.lecture.observeAsState()

    Log.d("lectureId", lectureId.toString())
    Scaffold(
        topBar = {
            TopAppBar(elevation = 3.dp) {
                Row {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })

                    Spacer(modifier = Modifier.width(28.dp))
                    if (lecture != null) {
                        Text(text = "Edit " + lecture!!.name)
                    }
                }
            }
        }

    ) {
        var updateLecture: Lecture = Lecture(
            id = lectureId,
            name = "",
            semester = "",
            lecturer = "",
            ects = 0.0f,
            sws = 0.0f,
            link = "",
            todo = 0.0f,
            done = 0.0f
        )

        //val lecture by lectureViewModel.lecture.observeAsState()

        //val lecture0: Lecture = lecture[0]

        if (lecture != null) {
            var name by remember { mutableStateOf(lecture!!.name) }
            var semester by remember { mutableStateOf(lecture!!.semester) }
            var lecturer by remember { mutableStateOf(lecture!!.lecturer) }
            var ects by remember { mutableStateOf(lecture!!.ects.toString()) }
            var sws by remember { mutableStateOf(lecture!!.sws.toString()) }
            var link by remember { mutableStateOf(lecture!!.link) }
            var todo by remember { mutableStateOf(lecture!!.todo) }
            var done by remember { mutableStateOf(lecture!!.done.toString()) }


            Card(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
            ) {
                Column(modifier = Modifier.padding(7.dp)) {
                    Text("Edit Lecture")

                    if (lecture != null) {
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = name,
                            onValueChange = { name = it },
                            singleLine = true,
                            label = { Text("Name") },
                            placeholder = { Text(text = "Lecturename") },
                        )
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = semester,
                            onValueChange = { semester = it },
                            singleLine = true,
                            label = { Text("Semester") },
                            placeholder = { Text(text = "like ss22") },
                        )
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = lecturer,
                            onValueChange = { lecturer = it },
                            singleLine = true,
                            label = { Text("Lecturer") },
                            placeholder = { Text(text = "Name of Lecturer") },
                        )
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = ects,
                            onValueChange = { ects = it },
                            singleLine = true,
                            label = { Text("ECTS") },
                            placeholder = { Text(text = "number of ects") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = sws,
                            onValueChange = { sws = it },
                            singleLine = true,
                            label = { Text("SWS") },
                            placeholder = { Text(text = "number of sws") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = link,
                            onValueChange = { link = it },
                            singleLine = true,
                            label = { Text("Link") },
                            placeholder = { Text(text = "Link of Lecture") },
                        )
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = done,
                            onValueChange = { done = it },
                            singleLine = true,
                            label = { Text("Studyhours") },
                            placeholder = { Text(text = "Hours you have studied") },
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (name.isNotEmpty() && ects.isNotEmpty() && sws.isNotEmpty()) {
                                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY)
                                val currentDate = sdf.format(Date())
                                val calc: Float
                                val hours: Float

                                hours = ects.toFloat() * 25

                                calc = hours - sws.toFloat() * 15.0f * 0.45f

                                val newLecture = Lecture(
                                    id = lectureId,
                                    name = name,
                                    semester = semester,
                                    lecturer = lecturer,
                                    ects = ects.toFloat(),
                                    sws = sws.toFloat(),
                                    link = link,
                                    todo = calc,
                                    done = done.toFloat()
                                )

                                lectureViewModel.editLecture(newLecture)

                                name = ""
                                semester = ""
                                lecturer = ""
                                ects = ""
                                sws = ""
                                link = ""
                                navController.popBackStack()
                            }

                        }) {
                        Text(text = "Save", fontSize = 22.sp)
                    }
                }
            }
        }
    }

}

