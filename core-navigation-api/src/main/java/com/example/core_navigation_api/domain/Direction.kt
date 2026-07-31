package com.example.core_navigation_api.domain

interface Direction {
    val screenName: String
        get() = javaClass.simpleName
}