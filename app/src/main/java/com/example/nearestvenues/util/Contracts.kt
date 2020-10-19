package com.example.nearestvenues.util




interface Contracts {

    interface view {
        fun searchVenue()
        fun startRequestQue()
        fun getLocationAsString() : String
        fun updateListView()
        fun startAction()
        fun textAdded()


    }

    interface presenter {
        fun searchForVenues()
        fun startRequestQue()
        fun setLocation() : String
        fun updateListView()
        fun startAction()
        fun textAdded()

    }
}