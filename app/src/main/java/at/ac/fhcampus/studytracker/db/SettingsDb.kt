package at.ac.fhcampus.studytracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import at.ac.fhcampus.studytracker.models.Settings


@Database(
    entities = [Settings::class],
    version = 1,
    exportSchema = false
)


abstract class SettingsDb : RoomDatabase() {

    abstract fun SettingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: SettingsDb? = null

        fun getDatabase(context: Context): SettingsDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }


        }

        private fun buildDatabase(context: Context): SettingsDb {
            return Room
                .databaseBuilder(context, SettingsDb::class.java, "settings_database")
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // happens on create

                            //var setting = Settings(id = 1, name = "User", goal = 4500.0f, "31.12.2099")
                            //INSTANCE?.SettingsDao()?.addSettings(setting)
/*
                            INSTANCE?.let { database ->

                                var setting = Settings(id = 1, name = "User", goal = 4500.0f,"31.12.2099")
                                database.SettingsDao().addSettings(setting)
                            }*/

                        }

                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }

    }

}