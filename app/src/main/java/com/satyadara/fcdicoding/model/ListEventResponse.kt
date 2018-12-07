package com.satyadara.fcdicoding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListEventResponse(
    val events: List<Event>?
) : Parcelable