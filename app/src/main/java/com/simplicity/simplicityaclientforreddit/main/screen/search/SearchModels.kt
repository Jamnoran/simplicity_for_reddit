package com.simplicity.simplicityaclientforreddit.main.screen.search

data class Data(val searchResult: List<String> = emptyList(), val query: String = "") {
    companion object {
        fun preview(): Data {
            return Data(searchResult = listOf("r/sub_reddit"), query = "")
        }
    }
}

data class SearchInput(val temp: String = "")
