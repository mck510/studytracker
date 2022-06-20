package at.ac.fhcampus.studytracker.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModel


@Composable
fun NewLectureScreen(navController: NavController, viewModel: LectureViewModel) {
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
                    Text(text = "New Lecture")
                }
            }
        }

    ) {
        Column(modifier = Modifier.padding(7.dp)) {
            var name by remember { mutableStateOf("") }
            var semester by remember { mutableStateOf("") }
            var lecturer by remember { mutableStateOf("") }
            var ects by remember { mutableStateOf("") }
            var sws by remember { mutableStateOf("") }
            var link by remember { mutableStateOf("") }
            var todo by remember { mutableStateOf("") }
            var done by remember { mutableStateOf("") }
            Card(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
            ) {
                Column(modifier = Modifier.padding(7.dp)) {
                    Text("Add New Lecture")


                    TextField(
                        modifier = Modifier.padding(8.dp),
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        label = { Text("Name*") },
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
                        label = { Text("ECTS*") },
                        placeholder = { Text(text = "number of ects") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        modifier = Modifier.padding(8.dp),
                        value = sws,
                        onValueChange = { sws = it },
                        singleLine = true,
                        label = { Text("SWS*") },
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
                    Spacer(modifier = Modifier.size(20.dp))
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (name.isNotEmpty() && ects.isNotEmpty() && sws.isNotEmpty()) {
                                //val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY)
                                //val currentDate = sdf.format(Date())
                                val calc: Float
                                val hours: Float

                                hours = ects.toFloat() * 25

                                calc = hours - sws.toFloat() * 15.0f * 0.45f

                                val newLecture = Lecture(
                                    name = name,
                                    semester = semester,
                                    lecturer = lecturer,
                                    ects = ects.toFloat(),
                                    sws = sws.toFloat(),
                                    link = link,
                                    todo = calc,
                                    done = 0.0f
                                )

                                viewModel.addLecture(newLecture)

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

            ///var lecture = Lecture(name = "test", semester = "ss22", lecturer = "tssst",ects=5,sws=2, link = "link", todo = 1.1f, done = 0.2f)


        }
    }
}













