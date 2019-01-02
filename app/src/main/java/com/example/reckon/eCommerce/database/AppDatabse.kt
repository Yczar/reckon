package com.example.reckon.eCommerce.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartData::class], version = 1)
abstract class AppDatabse: RoomDatabase() {
    abstract fun feedDao(): AppDao

    /**We need static access to the methods inhere*/
    companion object {
        @Volatile
        var INSTANCE: AppDatabse? = null

        fun getAppDatabse(context: Context): AppDatabse? {
            if (INSTANCE == null){
                synchronized(AppDatabse::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabse::class.java, "reckonDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDatabase(){
            INSTANCE = null
        }
    }
}