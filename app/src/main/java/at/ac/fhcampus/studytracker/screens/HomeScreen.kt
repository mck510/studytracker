package at.ac.fhcampus.studytracker.screens

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.models.Settings
import at.ac.fhcampus.studytracker.navigation.StudyTrackerScreens
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModel
import at.ac.fhcampus.studytracker.viewmodels.SettingsViewModel
import at.ac.fhcampus.studytracker.widgets.LectureCards
import at.ac.fhcampus.studytracker.widgets.addHours
import at.ac.fhcampus.studytracker.widgets.lectureCard
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun HomeScreen(
    navController: NavController,
    lectureViewModel: LectureViewModel,
    settingsViewModel: SettingsViewModel
) {
    var showMenu by remember {
        mutableStateOf(false)
    }


    val lectures: List<Lecture> by lectureViewModel.lectures.collectAsState()



    val settings by settingsViewModel.settings.observeAsState()


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "StudyTracker") }, actions = {

                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                }
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(onClick = {
                        navController.navigate(StudyTrackerScreens.NewLectureScreen.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "Add New",
                            modifier = Modifier
                                .padding(4.dp)
                                .align(alignment = Alignment.CenterVertically)
                        )
                        Text(
                            text = "Add New",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(120.dp)
                        )
                    }
                    if(lectures.isNotEmpty()){
                        DropdownMenuItem(onClick = {
                            navController.navigate(StudyTrackerScreens.EditLecturesScreen.name)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit, contentDescription = "Edit",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .align(alignment = Alignment.CenterVertically)
                            )
                            Text(
                                text = "Edit",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(120.dp)
                            )
                        }
                    }

                    DropdownMenuItem(onClick = {
                        navController.navigate(StudyTrackerScreens.SettingsScreen.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings, contentDescription = "Settings",
                            modifier = Modifier
                                .padding(4.dp)
                                .align(alignment = Alignment.CenterVertically)
                        )
                        Text(
                            text = "Settings",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(120.dp)
                        )
                    }
                }
            })
        }

    ) {

        var all = listOf(0.0f)
        lectures.forEach { lecture ->
            all += lecture.done
        }

        Column(
            modifier = Modifier
                .padding(7.dp)
        ) {
            if(lectures.isEmpty()){
                Card(
                    backgroundColor = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(7.dp)
                        .fillMaxSize(),
                    shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(7.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text="Welcome to StudyTracker\n", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                        Text(text="The App of your choice \nto track your Study Hours",modifier = Modifier.padding(8.dp))
                        Text(text="Go to Settings to set a Goal",modifier = Modifier.padding(8.dp))
                        Text(text="Add Lectures to track your Progress",modifier = Modifier.padding(8.dp))
                    }
                }


            }else{
            Card(
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(7.dp)
                )
                {



                    if (settings != null) {
                        Text(fontSize = 20.sp, text = "Hallo " + settings!!.name)
                        Text(text = "Goal: " + settings!!.goal.toString() + "h")
                        Text(text = "until: " + settings!!.date)

                        Text(text = "currently: " + all.sum().toString() + "h")

                        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY)
                        val currentDate = sdf.format(Date())

                        //Text(text = currentDate.toString())


                        var date = settings!!.date

                        var goal = settings!!.goal

                        var all0 = all.sum()

                        val firstDate: Date = sdf.parse(date)
                        val secondDate: Date = sdf.parse(currentDate)


                        val cmp = firstDate.compareTo(secondDate)
                        //val cmp = 0
                        when {
                            cmp > 0 -> {
                                //System.out.printf("%s is after %s", d1, d2)
                                //Text(text ="not reached")

                                if (all0 <= goal) {
                                    Text(text = "You have not reached your goal!")
                                } else {
                                    Text(
                                        text = "You have reached your goal!",
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "Congratulations!",
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 20.sp
                                    )
                                }


                            }
                            cmp < 0 -> {
                                //System.out.printf("%s is before %s", d1, d2)
                                //Text(text ="date reached")

                                if (all0 <= goal) {
                                    Text(
                                        text = "You could not reach your goal",
                                        color = MaterialTheme.colors.secondaryVariant
                                    )
                                } else {
                                    Text(
                                        text = "You have reached your goal!",
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "Congratulations!",
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 20.sp
                                    )
                                }

                            }
                            else -> {
                                if (all0 <= goal) {
                                    Text(text = "You have not reached your goal!")
                                } else {
                                    Text(
                                        text = "You have reached your goal!",
                                        color = MaterialTheme.colors.secondaryVariant
                                    )
                                    Text(
                                        text = "Congratulations!",
                                        color = MaterialTheme.colors.secondaryVariant
                                    )
                                }
                            }
                        }


                    } else {
                        Text(text = "Please see Settings!")
                        Text(text = "currently: " + all.sum().toString() + "h")
                    }

                    }
                }


            }

            LectureCards(lectures, lectureViewModel) { lecture ->
                addHours(lecture = lecture, viewModel = lectureViewModel)
            }


        }


    }
}

