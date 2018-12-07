package com.satyadara.fcdicoding.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.satyadara.fcdicoding.detailevent.DetailEventActivity
import com.satyadara.fcdicoding.R
import com.satyadara.fcdicoding.model.Event
import kotlinx.android.synthetic.main.item_football_event.view.*
import org.jetbrains.anko.startActivity
import java.util.*

class EventAdapter(val context: Context, val items: ArrayList<Event>, val isLastEvent: Boolean) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_football_event, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item: Event = items.get(position)
        holder.tvDate.text = item.dateEvent
        holder.tvTeamA.text = item.strHomeTeam
        holder.tvTeamB.text = item.strAwayTeam
        holder.tvTeamAScore.text = if (item.intHomeScore == null) "0" else item.intHomeScore.toString()
        holder.tvTeamBScore.text = if (item.intAwayScore == null) "0" else item.intAwayScore.toString()

        holder.lView.setOnClickListener {
            context.startActivity<DetailEventActivity>(DetailEventActivity.ITEM to item, DetailEventActivity.IS_LAST_EVENT to isLastEvent)
        }
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate = view.tvDate!!
        val tvTeamA = view.tvTeamA!!
        val tvTeamAScore = view.tvTeamAScore!!
        val tvTeamB = view.tvTeamB!!
        val tvTeamBScore = view.tvTeamBScore!!
        val lView = view
    }
}