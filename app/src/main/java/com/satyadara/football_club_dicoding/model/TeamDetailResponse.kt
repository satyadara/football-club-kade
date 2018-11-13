package com.satyadara.football_club_dicoding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamDetailResponse(
    val teams: List<Team>
) : Parcelable