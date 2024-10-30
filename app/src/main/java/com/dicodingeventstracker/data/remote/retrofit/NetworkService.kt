package com.dicodingeventstracker.data.remote.retrofit

import com.dicodingeventstracker.data.remote.response.ResponseDetailEvents
import com.dicodingeventstracker.data.remote.response.ResponseEvents
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {


    @GET("events")
    suspend fun getEvents(
        @Query("active")active:Int
    ): Response<ResponseEvents>

    @GET("events")
    suspend fun searchEvents(
        @Query("active")active:Int,
        @Query("q")keyword:String
    ): Response<ResponseEvents>

    @GET("events/{id}")
    suspend fun getDetailEvents(
        @Path("id")id:Int
    ): Response<ResponseDetailEvents>

}