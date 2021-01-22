package com.example.webflux.firstwebflux.dto

data class TodoRequest(
    val id: Int = 0,
    val todo: String,
)