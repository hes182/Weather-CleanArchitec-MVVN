package com.example.weathertraincleanarchit.presentation.state

import com.example.weathertraincleanarchit.domain.searchResultModel.Search_Results

data class SearchResultsState (
    val isLoading : Boolean = false,
    val data : List<Search_Results>? = null,
    val error : String? = null
)