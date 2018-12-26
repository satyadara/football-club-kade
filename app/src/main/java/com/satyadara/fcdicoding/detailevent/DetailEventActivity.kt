package com.satyadara.fcdicoding.detailevent

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.satyadara.fcdicoding.R
import com.satyadara.fcdicoding.db.FavoriteEvent
import com.satyadara.fcdicoding.model.Event
import com.satyadara.fcdicoding.model.Team
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class DetailEventActivity : AppCompatActivity(), AnkoLogger, DetailEventService.View {
    companion object {
        const val ITEM = "item"
        const val FAV_ITEM = "fav_item"
    }

    lateinit var presenter: DetailEventService.Presenter
    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        presenter = DetailEventPresenter(this)

        if (intent.getBooleanExtra(FAV_ITEM, false)) {
            val fav: FavoriteEvent = intent.getParcelableExtra(ITEM)
            presenter.getEventDetailById(fav.idEvent)
        } else {
            event = intent.getParcelableExtra(ITEM)
            setDetailEventText(event)
        }

    }

    override fun setDetailEventText(event: Event?) {
        if (event == null)   {
            return
        }
        this.event = event
        tvDate.text = event?.dateEvent
        tvScore.text = "${event?.intHomeScore} vs ${event?.intAwayScore}"
        tvTeamA.text = event?.strHomeTeam?.replace(';', '\n')
        tvTeamB.text = event?.strAwayTeam?.replace(';', '\n')
        tvGoalsA.text = event?.strHomeGoalDetails?.replace(';', '\n')
        tvGoalsB.text = event?.strAwayGoalDetails?.replace(';', '\n')
        tvShotsA.text = if (event?.intHomeShots.toString() == "null") "0" else event?.intHomeShots.toString()
        tvShotsB.text = if (event?.intAwayShots.toString() == "null") "0" else event?.intAwayShots.toString()
        tvGoalKeeperA.text = event?.strHomeLineupGoalkeeper?.replace(';', '\n')
        tvGoalKeeperB.text = event?.strAwayLineupGoalkeeper?.replace(';', '\n')
        tvDefenseA.text = event?.strHomeLineupDefense?.replace(';', '\n')
        tvDefenseB.text = event?.strAwayLineupDefense?.replace(';', '\n')
        tvMidA.text = event?.strHomeLineupMidfield?.replace(';', '\n')
        tvMidB.text = event?.strAwayLineupMidfield?.replace(';', '\n')
        tvForwardA.text = event?.strHomeLineupForward?.replace(';', '\n')
        tvForwardB.text = event?.strAwayLineupForward?.replace(';', '\n')
        tvSubstitutesA.text = event?.strHomeLineupSubstitutes?.replace(';', '\n')
        tvSubstitutesB.text = event?.strAwayLineupSubstitutes?.replace(';', '\n')

        info { event.toString() }
        title = event?.strEvent

        setStateFavorite(eventId = event?.idEvent!!)
        presenter.loadBadgeTeams(event)

        favorite_icon.setOnClickListener {
            if (presenter.stateFavorite(event?.idEvent!!)) removeFavoriteEvent()
            else setFavoriteEvent()

            setStateFavorite(event?.idEvent!!)
        }
    }

    override fun sendHomeBadgeTeam(team: Team) {
        Glide
            .with(applicationContext)
            .load(team.strTeamBadge)
            .into(ivTeamA)
    }

    override fun sendAwayBadgeTeam(team: Team) {
        Glide
            .with(applicationContext)
            .load(team.strTeamBadge)
            .into(ivTeamB)
    }

    override fun getContext(): Context {
        return this
    }

    override fun setStateFavorite(eventId: String) {
        if (presenter.stateFavorite(eventId)) favorite_icon.setImageResource(R.drawable.ic_favorite_48dp)
        else favorite_icon.setImageResource(R.drawable.ic_favorite_border_48dp)
    }

    override fun setFavoriteIconVisibility(state: Boolean) {
        if (state) favorite_icon.visibility = View.VISIBLE
        else favorite_icon.visibility = View.INVISIBLE
    }

    override fun setFavoriteEvent() {
        presenter.pushFavoriteEvent(event)
    }

    override fun removeFavoriteEvent() {
        presenter.removeFavoriteEvent(event?.idEvent!!)
    }

}
