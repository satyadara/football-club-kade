package com.satyadara.fcdicoding.main

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.satyadara.fcdicoding.R
import com.satyadara.fcdicoding.adapter.EventAdapter
import com.satyadara.fcdicoding.adapter.FavEventAdapter
import com.satyadara.fcdicoding.db.FavoriteEvent
import com.satyadara.fcdicoding.model.Event
import com.satyadara.fcdicoding.retrofit.RetrofitClient
import com.satyadara.fcdicoding.retrofit.service.EventService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainService.View {
    lateinit var mRecyclerView: RecyclerView

    lateinit var presenter: MainService.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mRecyclerView = recyclerView as RecyclerView
        presenter = MainPresenter(this)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        presenter.getNextEvent()
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
                presenter.getNextEvent()
            }
            R.id.nav_last_event -> {
                presenter.getLastEvent()
            }
            R.id.nav_fav -> {
                presenter.getFavEvent()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun sendLastEvent(list: ArrayList<Event>) {
        title = "LAST EVENT"
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = EventAdapter(
            this,
            list
        )
    }

    override fun sendNextEvent(list: ArrayList<Event>) {
        title = "NEXT EVENT"
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = EventAdapter(
            this,
            list
        )
    }

    override fun sendFavEvent(list: ArrayList<FavoriteEvent>) {
        title = "FAVORITE EVENT"
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = FavEventAdapter(
            this,
            list
        )
    }

    override fun getContext(): Context {
        return this
    }

}
