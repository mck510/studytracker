package at.ac.fhcampus.studytracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampus.studytracker.db.LecturesDb
import at.ac.fhcampus.studytracker.db.SettingsDb
import at.ac.fhcampus.studytracker.repositories.LectureRepository
import at.ac.fhcampus.studytracker.repositories.SettingsRepository
import at.ac.fhcampus.studytracker.screens.EditLecturesScreen
import at.ac.fhcampus.studytracker.screens.EditSingleLectureScreen
import at.ac.fhcampus.studytracker.screens.HomeScreen
import at.ac.fhcampus.studytracker.screens.SettingsScreen
import at.ac.fhcampus.studytracker.screens.NewLectureScreen
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModel
import at.ac.fhcampus.studytracker.viewmodels.LectureViewModelFactory
import at.ac.fhcampus.studytracker.viewmodels.SettingsViewModel
import at.ac.fhcampus.studytracker.viewmodels.SettingsViewModelFactory

@Composable
fun StudyTrackerNavigation() {
    val navController = rememberNavController() // create NavController instance

    val context = LocalContext.current
    val db = LecturesDb.getDatabase(context = context)
    val repository = LectureRepository(dao = db.LectureDao())
    val lectureViewModel: LectureViewModel = viewModel(
        factory = LectureViewModelFactory(repository = repository, lectureId = null)
    )

    val db2 = SettingsDb.getDatabase(context = context)
    val repository2 = SettingsRepository(dao = db2.SettingsDao())
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(repository = repository2, settingsId = null)
    )



    NavHost(navController = navController, startDestination = StudyTrackerScreens.HomeScreen.name) {
        composable(route = StudyTrackerScreens.HomeScreen.name) {
            HomeScreen(
                navController,
                lectureViewModel = lectureViewModel,
                settingsViewModel = settingsViewModel
            )
        }
        composable(route = StudyTrackerScreens.SettingsScreen.name) { backStackEntry ->
            SettingsScreen(
                navController = navController,
                settingsViewModel = settingsViewModel,
                lectureViewModel = lectureViewModel
            )
        }
        composable(route = StudyTrackerScreens.NewLectureScreen.name) { backStackEntry ->
            NewLectureScreen(navController = navController, viewModel = lectureViewModel)
        }
        composable(route = StudyTrackerScreens.EditLecturesScreen.name) { backStackEntry ->
            EditLecturesScreen(navController = navController, viewModel = lectureViewModel)
        }
        composable(
            route = StudyTrackerScreens.EditSingleLectureScreen.name + "/{lectureId}",
            arguments = listOf(navArgument("lectureId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            EditSingleLectureScreen(
                lectureId = backStackEntry.arguments?.getString("lectureId")?.toLong(),
                navController = navController,
                repository = repository

            )
        }


    }
}