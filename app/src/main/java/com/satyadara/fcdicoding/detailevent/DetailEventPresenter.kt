package com.satyadara.fcdicoding.detailevent

import android.database.sqlite.SQLiteConstraintException
import com.satyadara.fcdicoding.db.FavoriteEvent
import com.satyadara.fcdicoding.db.database
import com.satyadara.fcdicoding.model.Event
import com.satyadara.fcdicoding.retrofit.RetrofitClient
import com.satyadara.fcdicoding.retrofit.service.EventService
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DetailEventPresenter(val view: DetailEventService.View) : DetailEventService.Presenter {
    override fun getEventDetailById(idEvent: String) {
        val retrofit = RetrofitClient.getClient()
        val service = retrofit.create(EventService::class.java)
        service.detailEventById(idEvent)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val event = json.events!![0]
                    view.setDetailEventText(event)
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )
    }

    override fun loadBadgeTeams(event: Event?) {
        val retrofit = RetrofitClient.getClient()
        val service = retrofit.create(EventService::class.java)
        // Home Team
        service.detailTeamById(event?.idHomeTeam)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val team = json.teams[0]
                    view.sendHomeBadgeTeam(team)
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )

        // Away Team
        service.detailTeamById(event?.idAwayTeam)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val team = json.teams[0]
                    view.sendAwayBadgeTeam(team)
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )
    }

    override fun pushFavoriteEvent(event: Event?) {
        try {
            view.getContext().database.use {
                insert(
                    FavoriteEvent.TABLE_FAVORITE,
                    FavoriteEvent.FAVORITE_EVENT_ID to event!!.idEvent,
                    FavoriteEvent.FAVORITE_HOME_ID to event.idHomeTeam.toString(),
                    FavoriteEvent.FAVORITE_AWAY_ID to event.idAwayTeam.toString(),
                    FavoriteEvent.FAVORITE_HOME_TEAM to event.strHomeTeam,
                    FavoriteEvent.FAVORITE_AWAY_TEAM to event.strAwayTeam,
                    FavoriteEvent.FAVORITE_LEAGUE to event.strLeague
                )
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    override fun removeFavoriteEvent(eventId: String) {
        try {
            view.getContext().database.use {
                delete(
                    FavoriteEvent.TABLE_FAVORITE,
                    "(" + FavoriteEvent.FAVORITE_EVENT_ID + " = {id})", "id" to eventId
                )
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }

    }

    override fun stateFavorite(eventId: String): Boolean {
        var state = false
        view.getContext().database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
                .whereArgs("(" + FavoriteEvent.FAVORITE_EVENT_ID + " = {id})", "id" to eventId)
            val favorite = result.parseList(classParser<FavoriteEvent>())

            state = !favorite.isEmpty()

        }
        return state
    }

}