package com.satyadara.football_club_dicoding.model

import android.os.Parcelable
import com.satyadara.football_club_dicoding.model.Event
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListEventResponse(
    val events: List<Event>?
) : Parcelable