package com.satyadara.fcdicoding.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "favorite.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            FavoriteEvent.TABLE_FAVORITE, true,
            FavoriteEvent.FAVORITE_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteEvent.FAVORITE_EVENT_ID to TEXT,
            FavoriteEvent.FAVORITE_HOME_ID to TEXT,
            FavoriteEvent.FAVORITE_AWAY_ID to TEXT,
            FavoriteEvent.FAVORITE_AWAY_TEAM to TEXT,
            FavoriteEvent.FAVORITE_HOME_TEAM to TEXT,
            FavoriteEvent.FAVORITE_LEAGUE to TEXT
        )


        db.createTable(
            FavoriteTeam.TABLE_TEAM, true,
            FavoriteTeam.TEAM_FAVORITE_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_COUNTRY to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_STADIUM to TEXT,
            FavoriteTeam.TEAM_FORMED_YEAR to TEXT,
            FavoriteTeam.TEAM_DESCRIPTION to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropIndex(FavoriteEvent.TABLE_FAVORITE, true)
        db.dropIndex(FavoriteTeam.TABLE_TEAM, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)
