package com.example.nearestvenues

import com.example.nearestvenues.util.Contracts


class Presenter(val myView : Contracts.view) : Contracts.presenter {

    override fun searchForVenues() {
       return myView.searchVenue()
    }

    override fun startRequestQue() {
        myView.startRequestQue()
    }

    override fun startAction() {
        myView.startAction()

    }

    override fun setLocation() : String {
        return myView.getLocationAsString()
    }

    override fun updateListView() {
        myView.updateListView()
    }

    override fun textAdded() {
        myView.textAdded()
    }


}


