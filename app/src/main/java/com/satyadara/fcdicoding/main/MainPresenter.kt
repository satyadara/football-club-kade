package com.satyadara.fcdicoding.main

import com.satyadara.fcdicoding.db.FavoriteEvent
import com.satyadara.fcdicoding.db.database
import com.satyadara.fcdicoding.model.Event
import com.satyadara.fcdicoding.retrofit.RetrofitClient
import com.satyadara.fcdicoding.retrofit.service.EventService
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainPresenter(val view: MainService.View) : MainService.Presenter {

    override fun getLastEvent() {
        val retrofit = RetrofitClient.getClient()
        val service = retrofit.create(EventService::class.java)
        service?.lastEvents("4328")!!
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val list: ArrayList<Event> = json.events as ArrayList<Event>
                    view.sendLastEvent(list)
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )
    }

    override fun getNextEvent() {
        val retrofit = RetrofitClient.getClient()
        val service = retrofit.create(EventService::class.java)
        service?.nextEvents("4328")!!
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val list: ArrayList<Event> = json.events as ArrayList<Event>
                    view.sendNextEvent(list)
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )
    }

    override fun getFavEvent() {
        val favorites: MutableList<FavoriteEvent> = mutableListOf()
        view.getContext().database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            favorites.addAll(favorite)

        }
        view.sendFavEvent(ArrayList(favorites))
    }

}