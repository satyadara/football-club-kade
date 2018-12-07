package com.satyadara.fcdicoding.detailevent

import android.content.Context
import com.satyadara.fcdicoding.model.Event
import com.satyadara.fcdicoding.model.Team

interface DetailEventService {
    interface Presenter {
        fun loadBadgeTeams(event: Event?)
        fun pushFavoriteEvent(event: Event?)
        fun removeFavoriteEvent(eventId: String)
        fun stateFavorite(eventId: String): Boolean
    }

    interface View {
        fun getContext(): Context
        fun sendHomeBadgeTeam(team: Team)
        fun sendAwayBadgeTeam(team: Team)
        fun setFavoriteIconVisibility(state: Boolean)
        fun setStateFavorite(eventId: String)
        fun setFavoriteEvent()
        fun removeFavoriteEvent()
    }
}