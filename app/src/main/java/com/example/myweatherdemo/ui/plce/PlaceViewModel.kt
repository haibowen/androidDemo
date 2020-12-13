package com.example.myweatherdemo.ui.plce

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myweatherdemo.logic.Repository
import com.example.myweatherdemo.logic.model.Place

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlace(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}