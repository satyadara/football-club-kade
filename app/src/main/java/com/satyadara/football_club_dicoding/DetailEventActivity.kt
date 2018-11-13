package com.satyadara.football_club_dicoding

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.satyadara.football_club_dicoding.adapter.EventAdapter
import com.satyadara.football_club_dicoding.model.Event
import com.satyadara.football_club_dicoding.retrofit.RetrofitClient
import com.satyadara.football_club_dicoding.retrofit.service.EventService
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit.Retrofit
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DetailEventActivity : AppCompatActivity(), AnkoLogger {
    companion object {
        val ITEM = "item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        val event: Event? = intent.getParcelableExtra(ITEM)

        info { event.toString() }
        title = event?.strEvent

        setDetailEventText(event)
        setTeamBadge(event)
    }

    fun setDetailEventText(event: Event?) {
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
    }

    fun setTeamBadge(event: Event?) {
        val retrofit = RetrofitClient.getClient()
        val service = retrofit.create(EventService::class.java)
        // Home Team
        service?.detailTeamById(event?.idHomeTeam)!!
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val team = json.teams[0]
                    Glide
                        .with(applicationContext)
                        .load(team.strTeamBadge)
                        .into(ivTeamA)
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )

        // Away Team
        service?.detailTeamById(event?.idAwayTeam)!!
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val team = json.teams[0]
                    Glide
                        .with(applicationContext)
                        .load(team.strTeamBadge)
                        .into(ivTeamB)
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )
    }
}
