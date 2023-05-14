package com.example.firebasekotlin.models

    data class EmployeeModel(
        var placeId: String? = null,
        var placeName: String? = null,
        var description: String? = null,
        var importance: String? = null,

        var image: String? = null
    )
