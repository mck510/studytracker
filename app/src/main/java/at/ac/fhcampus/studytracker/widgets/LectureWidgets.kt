package at.ac.fhcampus.studytracker.widgets

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import at.ac.fhcampus.studytracker.models.Lecture
import at.ac.fhcampus.studytracker.navigation.StudyTrackerScreens
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun LectureCards(lectures: List<Lecture>, viewModel: LectureViewModel,content: @Composable (lecture : Lecture) -> Unit = {}){
    LazyColumn {

        items(lectures){ lecture ->

            lectureCard(lecture = lecture, viewModel = viewModel){
               content(lecture)
            }

        }
    }
}
@Composable
fun lectureCard(lecture: Lecture, viewModel: LectureViewModel,content: @Composable () -> Unit = {}){
    Card(modifier = Modifier
        .padding(7.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
    ) {
        Row {

            Column(modifier = Modifier.padding(7.dp)) {
                Text(lecture.name, fontSize = 20.sp)
                if(lecture.semester != ""){
                    Text(lecture.semester)
                }
                if(lecture.lecturer != ""){
                    Text(lecture.lecturer)
                }

                Text("ECTS: "+lecture.ects.toString())
                Text("SWS: "+lecture.sws.toString())

                if(lecture.link != "" && lecture.link != " " ) {

                    val context = LocalContext.current
                    val openURL = Intent(Intent.ACTION_VIEW)
                    var url = lecture.link

                    if(!url.startsWith("http://")&&!url.startsWith("https://")){
                        url = "https://$url"
                    }

                    openURL.data = Uri.parse(url)

                    ClickableText(
                        style = TextStyle(fontSize = 18.sp),
                        text = AnnotatedString("Link"),
                        onClick = { context.startActivity(createChooser(openURL, "Browse with")) })


                }

                //Text(lecture.link)
                Text("TODO: "+lecture.todo.toString()+"h")
                Text("DONE: "+lecture.done.toString()+"h")
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.End){

                content()
                //addHours(lecture,viewModel)
            }
        }
    }
}




@Composable
fun addHours(lecture: Lecture, viewModel: LectureViewModel){

    var new by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .padding(8.dp)
            .width(70.dp),
        value = new,
        onValueChange = { new = it },
        singleLine = true,
        label = { Text("0.0 h") },
        placeholder = { Text(text = " ") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Button(
        modifier = Modifier
            .padding(8.dp)
            .width(70.dp),
        onClick = {
        if (new.isNotEmpty()) {
            //val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY)
            //val currentDate = sdf.format(Date())


            new = new.replace(",",".")
            val done = lecture.done + new.toFloat()

            val newLecture = Lecture(
                id = lecture.id,
                name = lecture.name,
                semester = lecture.semester,
                lecturer = lecture.lecturer,
                ects = lecture.ects,
                sws = lecture.sws,
                link = lecture.link,
                todo = lecture.todo,
                done = done
            )

            viewModel.editLecture(newLecture)

            new = ""

        }

    }) {
        Text("+")
    }
}

@Composable
fun editLecture(navController: NavController, lecture: Lecture, viewModel: LectureViewModel){

    Column {
        Button(modifier = Modifier
            .padding(8.dp),onClick = {
            val lectureId = lecture.id
            navController.navigate(StudyTrackerScreens.EditSingleLectureScreen.name + "/$lectureId")

        }) {
            Text("Edit")
        }

        Button(modifier = Modifier
            .padding(8.dp),onClick = {

            viewModel.removeLecture(lecture)

        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }


    }

}

