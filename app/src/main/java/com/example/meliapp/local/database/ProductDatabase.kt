package com.example.meliapp.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.meliapp.local.entity.ItemProductEntity


   @Database(entities = [ItemProductEntity::class], version = 1)
   abstract class ProductDatabase : RoomDatabase() {
        abstract fun itemProductDao(): IPayment
       companion object {
           @Volatile
           private var INSTANCE : ProductDatabase? = null

           fun getDataBase(context: Context): ProductDatabase {
               val tempInstance = INSTANCE
               if (tempInstance != null) {
                   return tempInstance
               }
               synchronized(this) {
                   val instance = Room.databaseBuilder(context.applicationContext,
                       ProductDatabase::class.java,
                       "shopping")
                       .fallbackToDestructiveMigrationFrom(1,2)
                       .build()
                   INSTANCE = instance
                   return instance
               }
           }
       }
    }
