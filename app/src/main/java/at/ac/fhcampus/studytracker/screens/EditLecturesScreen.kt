package at.ac.fhcampus.studytracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.navigation.StudyTrackerScreens
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModel
import at.ac.fhcampus.studytracker.widgets.LectureCards
import at.ac.fhcampus.studytracker.widgets.addHours
import at.ac.fhcampus.studytracker.widgets.editLecture

@Composable
fun EditLecturesScreen(navController: NavController, viewModel: LectureViewModel) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(elevation = 3.dp) {
                Row {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })

                    Spacer(modifier = Modifier.width(28.dp))
                    Text(text = "Edit Lecture")
                }
            }
        }

    ) {

        Column {
            val lectures: List<Lecture> by viewModel.lectures.collectAsState()

            LectureCards(lectures, viewModel) { lecture ->
                editLecture(navController, lecture = lecture, viewModel = viewModel)
            }


        }


    }
}