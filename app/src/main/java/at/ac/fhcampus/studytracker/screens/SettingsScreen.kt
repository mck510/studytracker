package at.ac.fhcampus.studytracker.screens

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.models.Settings
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModel
import at.ac.fhcampus.studytracker.viewmodels.SettingsViewModel
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*
import kotlin.math.log


@Composable
fun SettingsScreen(
    navController: NavController,
    lectureViewModel: LectureViewModel,
    settingsViewModel: SettingsViewModel
) {

    val settings by settingsViewModel.settings.observeAsState()

    var settings0 = settings
/*
    var setting = Settings(id = 1, name = "User", goal = 4500.0f,"31.12.2099")
    viewModel.addSettings(setting)
    */

    if (settings0 == null) {
        settings0 = Settings(id = 1, name = "User", goal = 750.0f, "31.12.2099")
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
                    Text(text = "Settings")
                }
            }
        }

    ) {

        var name by remember { mutableStateOf(settings0.name) }
        var goal by remember { mutableStateOf(settings0.goal.toString()) }

        Column(modifier = Modifier.padding(7.dp)) {
            Card(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
            ) {
                //Text(text = settings0.name)

                if (settings0 != null) {
                    Column(modifier = Modifier.padding(7.dp)) {
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = name,
                            onValueChange = { name = it },
                            singleLine = true,
                            label = { Text("Name") },
                            placeholder = { Text(text = "Your Name") },
                        )
                        TextField(
                            modifier = Modifier.padding(8.dp),
                            value = goal,
                            onValueChange = { goal = it },
                            singleLine = true,
                            label = { Text("Goal") },
                            placeholder = { Text(text = "Goal as Float") },
                        )


                        val mContext = LocalContext.current

                        // Declaring integer values
                        // for year, month and day
                        val mYear: Int
                        val mMonth: Int
                        val mDay: Int

                        // Initializing a Calendar
                        val mCalendar = Calendar.getInstance()

                        // Fetching current year, month and day
                        mYear = mCalendar.get(Calendar.YEAR)
                        mMonth = mCalendar.get(Calendar.MONTH)
                        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

                        mCalendar.time = Date()


                        // Declaring a string value to
                        // store date in string format
                        val mDate = remember { mutableStateOf("") }
                        var Month = ""
                        var Day = ""
                        var Year = ""
                        var Date = ""
                        Date = settings0.date
                        // Declaring DatePickerDialog and setting
                        // initial values as current values (present year, month and day)
                        val mDatePickerDialog = DatePickerDialog(
                            mContext,
                            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->

                                Month = "${mMonth + 1}"
                                Day = "$mDayOfMonth"
                                Year = "$mYear"

                                //mDate.value = "$Day.${mMonth+1}.$mYear"

                                if (Day.length == 1) {
                                    Day = "0$Day"
                                }
                                if (Month.length == 1) {
                                    Month = "0$Month"
                                }
                                Date = "$Day.$Month.$Year"

                            }, mYear, mMonth, mDay
                        )


                        // Creating a button that on
                        // click displays/shows the DatePickerDialog
                        Button(modifier = Modifier.padding(8.dp), onClick = {
                            mDatePickerDialog.show()
                        }) {
                            Text(text = "Select Date", color = Color.White)
                        }

                        // Adding a space of 100dp height
                        Spacer(modifier = Modifier.size(20.dp))

                        // Displaying the mDate value in the Text
                        //Text(text = "Selected Date: ${mDate.value}", fontSize = 30.sp, textAlign = TextAlign.Center)


                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            onClick = {
                                if (name.isNotEmpty()) {
                                    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY)
                                    val currentDate = sdf.format(Date())
                                    var newSettings = Settings(
                                        id = 1,
                                        name = name,
                                        goal = goal.toFloat(),
                                        date = Date
                                    )

                                    if (settings == null) {
                                        settingsViewModel.addSettings(newSettings)
                                    } else {
                                        settingsViewModel.editSettings(newSettings)
                                    }

                                    navController.popBackStack()
                                }

                            }) {
                            Text(text = "Save", fontSize = 22.sp)
                        }


                    }
                }

            }



            Card(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
            )
            {

                Button(modifier = Modifier.padding(16.dp),
                    onClick = {
                        lectureViewModel.deleteAll()
                        settingsViewModel.deleteAll()
                        navController.popBackStack()
                    })
                {
                    Text("DELETE ALL", fontSize = 22.sp)
                }
            }
        }
    }

}

