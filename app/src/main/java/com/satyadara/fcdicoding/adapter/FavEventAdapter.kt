package com.satyadara.fcdicoding.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.satyadara.fcdicoding.R
import com.satyadara.fcdicoding.db.FavoriteEvent
import com.satyadara.fcdicoding.detailevent.DetailEventActivity
import com.satyadara.fcdicoding.model.Event
import kotlinx.android.synthetic.main.item_football_event.view.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class FavEventAdapter(val context: Context, val items: ArrayList<FavoriteEvent>) :
    RecyclerView.Adapter<FavEventAdapter.FavEventViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavEventViewHolder {
        return FavEventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_football_event, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavEventViewHolder, position: Int) {
        val item: FavoriteEvent = items.get(position)
        holder.tvDate.text = ""
        holder.tvTeamA.text = item.strHomeTeam
        holder.tvTeamB.text = item.strAwayTeam
        holder.tvTeamAScore.text = ""
        holder.tvTeamBScore.text = ""

        holder.lView.setOnClickListener {
            context.startActivity<DetailEventActivity>(
                DetailEventActivity.ITEM to item,
                DetailEventActivity.FAV_ITEM to true
            )
        }
    }

    class FavEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate = view.tvDate!!
        val tvTeamA = view.tvTeamA!!
        val tvTeamAScore = view.tvTeamAScore!!
        val tvTeamB = view.tvTeamB!!
        val tvTeamBScore = view.tvTeamBScore!!
        val lView = view
    }
}
