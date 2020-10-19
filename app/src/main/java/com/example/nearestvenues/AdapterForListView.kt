package com.example.nearestvenues

import android.content.Context
import android.widget.ArrayAdapter

class AdapterForListView(val context: Context, val venueList: MutableList<String>) {
    //initializing the listview adapter here
      val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, venueList)
}