package com.satyadara.fcdicoding.main

import android.content.Context
import com.satyadara.fcdicoding.db.FavoriteEvent
import com.satyadara.fcdicoding.model.Event

public interface MainService {
    interface Presenter {
        fun getLastEvent()
        fun getNextEvent()
        fun getFavEvent()
    }

    interface View {
        fun sendLastEvent(list: ArrayList<Event>)
        fun sendNextEvent(list: ArrayList<Event>)
        fun sendFavEvent(list: ArrayList<FavoriteEvent>)
        fun getContext(): Context
    }
}