package com.satyadara.fcdicoding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamDetailResponse(
    val teams: List<Team>
) : Parcelable