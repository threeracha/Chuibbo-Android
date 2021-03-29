package com.example.chuibbo_android.image

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Image::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imagedao() : ImageDao

    companion object{
        private var INSTANCE : ImageDatabase?= null
        fun getDatabase(context: Context): ImageDatabase{
            val temInstance = INSTANCE
            if(temInstance != null){
                return temInstance
            }
            synchronized(this) {
                var instance = Room.databaseBuilder(
                    context,ImageDatabase::class.java,"ItemDB").build()
                instance = Room.databaseBuilder(context.getApplicationContext(), ImageDatabase::class.java, "Sample.db").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}