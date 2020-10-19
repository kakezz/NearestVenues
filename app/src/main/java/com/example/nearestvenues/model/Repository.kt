package com.example.nearestvenues.model

import retrofit2.Response

//Repository class for viewmodelfactory method

class Repository {

    suspend fun getVenue(client_id : String, client_secret: String, version: Int,  ll : String, radius : Int) : Response<VenueItem> {
        return RetrofitIntance.restApi.getVenue(client_id, client_secret, version, ll, radius)
    }
}