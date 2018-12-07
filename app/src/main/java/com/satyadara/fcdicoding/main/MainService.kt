package com.satyadara.fcdicoding.main

import com.satyadara.fcdicoding.model.Event

public interface MainService {
    interface Presenter{
        fun getLastEvent()
        fun getNextEvent()
    }

    interface View{
        fun sendLastEvent(list: ArrayList<Event>)
        fun sendNextEvent(list: ArrayList<Event>)
    }
}