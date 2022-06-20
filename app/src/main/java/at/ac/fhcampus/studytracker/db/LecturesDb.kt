package at.ac.fhcampus.studytracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import at.ac.fhcampus.studytracker.models.Lecture

@Database(
    entities = [Lecture::class],
    version = 2,
    exportSchema = false
)


abstract class LecturesDb : RoomDatabase() {

    abstract fun LectureDao(): LectureDao

    companion object {
        @Volatile
        private var INSTANCE: LecturesDb? = null

        fun getDatabase(context: Context): LecturesDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }


        }

        private fun buildDatabase(context: Context): LecturesDb {
            return Room
                .databaseBuilder(context, LecturesDb::class.java, "lecture_database")
                .addCallback(
                    object : RoomDatabase.Callback() {

                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }

    }

}