package com.example.nearestvenues.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

//mainmodel for the responseobject

class MainViewModel(private val repository: Repository) : ViewModel() {
        val venueResponse: MutableLiveData<Response<VenueItem>> = MutableLiveData()
        fun getVenue(client_id : String, client_secret: String, version: Int, ll : String, radius : Int){
                viewModelScope.launch {
                        val response : Response<VenueItem> = repository.getVenue(client_id, client_secret, version, ll, radius)
                        venueResponse.value = response
                }
        }
}