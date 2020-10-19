package com.example.nearestvenues.model




import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
//API interface for GET request URL

interface  FoursquareAPI {
    @GET("venues/search/")
    suspend fun getVenue(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("v") version: Int,
        @Query("ll") ll: String,
        @Query("radius") radius: Int) : Response<VenueItem>



}