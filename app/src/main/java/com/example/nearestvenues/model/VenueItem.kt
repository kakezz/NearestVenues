package com.example.nearestvenues.model



//data model for JsonObject from GET Request

data class VenueItem(val response : Response)

data class Response(val venues : List<Venues>)

data class Venues(val name : String, val location: com.example.nearestvenues.model.Location)

data class Location(val address : String, val distance : Int, val postalCode : String)



