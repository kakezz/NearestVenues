package com.example.nearestvenues

import android.Manifest.permission.ACCESS_COARSE_LOCATION


import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.*

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


import androidx.lifecycle.ViewModelProvider
import com.example.nearestvenues.model.*
import com.example.nearestvenues.util.Constants
import com.example.nearestvenues.util.Contracts

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), Contracts.view {

    private lateinit var viewModel: MainViewModel
    private lateinit var locationManager: LocationManager
    private var searchedVenue = ""
    private lateinit var presenter: Presenter
    private var currentLocation: Location? = null
    private val venueList: MutableList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //making a handle for the presenter
        presenter = Presenter(this)
        presenter.startAction()


    } //OnCreate ends here

    //This method iterates through the search results and finds corresponding search result of the search input and adds them to listview
    override fun searchVenue() {
        venueList.clear()
        viewModel.venueResponse.observe(this, { response ->
            if (response.isSuccessful) {
                Log.d("teustaus", response.body().toString())
                Log.d("teustaus", response.raw().request.toString())
                response.body()!!.response.venues.forEach { venues ->
                    if ((venues.name).toLowerCase(Locale.ROOT)
                            .startsWith(searchedVenue.toLowerCase(Locale.ROOT))
                    ) {
                        Log.d(
                            "teustaus",
                            venues.name + ", meikä: ${venues.location.address}, ${venues.location.postalCode} " + " distance: ${venues.location.distance}"
                        )
                        venueList.add(venues.name + ". Address: ${(venues.location.address)}, ${venues.location.postalCode}." + " Distance: ${venues.location.distance} meters.")
                    }
                }
            } else {
                Toast.makeText(this, "Location data invalid", Toast.LENGTH_SHORT).show()
                Log.d("teustaus", response.errorBody().toString())
                Log.d("teustaus", response.raw().request.toString())
            }

        })

    }


    //forms the viewModel and makes the GET request
    override fun startRequestQue() {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //calls the GET function with parameters
        viewModel.getVenue(
            Constants.clientId,
            Constants.clientSecret,
            Constants.version,
            presenter.setLocation(),
            Constants.searchRadius
        )
    }

    //location to string (latitude, longitude)
    override fun getLocationAsString(): String {
        return "${currentLocation?.latitude}," + "${currentLocation?.longitude}"
    }

    //function for the listview adapter, uses the AdapterForlistView Class
    override fun updateListView() {
        if (searchedVenue.isEmpty()) venueList.clear()
        listview.adapter = AdapterForListView(this, venueList).adapter
    }

//Starts the whole process. Calls the iniation of Get reguest and and the search method for results.
//permission checks included
    override fun startAction() {
        when {
            ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED -> {
                locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                currentLocation = locationManager.getLastKnownLocation(NETWORK_PROVIDER)

                    presenter.startRequestQue()
                    presenter.textAdded()

        }
            ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION) -> {
            Toast.makeText(this, "Location permission needed", Toast.LENGTH_SHORT).show()
        }
            else -> {
            // directly ask for the permission.
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION), 111)
        }
        }

    }

    //edittext listener that compares the query to results, also handles the listview updates
    override fun textAdded() {

        searchfield.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchedVenue = s.toString()
                presenter.searchForVenues()

            }

            override fun afterTextChanged(s: Editable?) {
                presenter.updateListView()

            }
        }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            111 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PERMISSION_GRANTED))
                {
                    presenter.startAction()


                } else {
                    Toast.makeText(this, "No location permission", Toast.LENGTH_LONG).show()


                }
            }

        }

    }

    }





