package com.satyadara.fcdicoding.retrofit.service

import com.satyadara.fcdicoding.model.ListEventResponse
import com.satyadara.fcdicoding.model.TeamDetailResponse
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface EventService {
    @GET("eventsnextleague.php")
    fun nextEvents(@Query("id") id: String?): Observable<ListEventResponse>


    @GET("eventspastleague.php")
    fun lastEvents(@Query("id") id: String?): Observable<ListEventResponse>

    @GET("lookupteam.php")
    fun detailTeamById(@Query("id") id: String?): Observable<TeamDetailResponse>

}