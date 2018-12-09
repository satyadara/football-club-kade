package com.satyadara.fcdicoding.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteEvent(
    val idFavorite: Long?,
    val idEvent: String,
    val idHome: String,
    val idAway: String,
    val strAwayTeam: String,
    val strHomeTeam: String,
    val strLeague: String
) : Parcelable {
    companion object {
        const val TABLE_FAVORITE: String = "tbl_favorite"
        const val FAVORITE_ID: String = "fav_id"
        const val FAVORITE_EVENT_ID: String = "fav_id_event"
        const val FAVORITE_HOME_ID: String = "fav_id_home"
        const val FAVORITE_AWAY_ID: String = "fav_id_away"
        const val FAVORITE_AWAY_TEAM: String = "fav_away_team"
        const val FAVORITE_HOME_TEAM: String = "fav_home_team"
        const val FAVORITE_LEAGUE: String = "fav_league"
    }
}