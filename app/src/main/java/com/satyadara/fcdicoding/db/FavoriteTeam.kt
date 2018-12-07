package com.satyadara.fcdicoding.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTeam(
    val idFavorite: Long?,
    val idTeam: String,
    val strTeam: String,
    var strCountry: String,
    var strTeamBadge: String,
    var strStadium: String,
    var intFormedYear: String,
    var strDescriptionEN: String
) : Parcelable {

    companion object {
        const val TABLE_TEAM: String = "team_tbl"
        const val TEAM_FAVORITE_ID: String = "team_id_favorite"
        const val TEAM_ID: String = "team_id"
        const val TEAM_NAME: String = "team_name"
        const val TEAM_COUNTRY: String = "team_country"
        const val TEAM_BADGE: String = "team_badge"
        const val TEAM_STADIUM: String = "team_stadium"
        const val TEAM_FORMED_YEAR: String = "team_year"
        const val TEAM_DESCRIPTION: String = "team_desc"
    }
}