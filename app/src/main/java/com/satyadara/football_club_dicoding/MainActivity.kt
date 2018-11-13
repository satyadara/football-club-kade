package com.satyadara.football_club_dicoding

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.satyadara.football_club_dicoding.adapter.EventAdapter
import com.satyadara.football_club_dicoding.fragment.EventListFragment
import com.satyadara.football_club_dicoding.model.Event
import com.satyadara.football_club_dicoding.retrofit.RetrofitClient
import com.satyadara.football_club_dicoding.retrofit.service.EventService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mRecyclerView = recyclerView as RecyclerView

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        nextEvent()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        mRecyclerView.adapter = null
        when (item.itemId) {
            R.id.nav_next_event -> {
                nextEvent()
            }
            R.id.nav_last_event -> {
                lastEvent()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun nextEvent() {
        title = "NEXT EVENT"
        val retrofit = RetrofitClient.getClient()
        val service = retrofit.create(EventService::class.java)
        service?.nextEvents("4328")!!
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val list: ArrayList<Event> = json.events as ArrayList<Event>
                    mRecyclerView.layoutManager = LinearLayoutManager(this)
                    mRecyclerView.adapter = EventAdapter(
                        this,
                        list
                    )
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )
    }

    private fun lastEvent() {
        title = "LAST EVENT"
        val retrofit = RetrofitClient.getClient()
        val service = retrofit.create(EventService::class.java)
        service?.lastEvents("4328")!!
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    val list: ArrayList<Event> = json.events as ArrayList<Event>
                    mRecyclerView.layoutManager = LinearLayoutManager(this)
                    mRecyclerView.adapter = EventAdapter(
                        this,
                        list
                    )
                },
                { error ->
                    kotlin.error("Error tenan : $error")
                }
            )
    }
}
