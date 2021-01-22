package com.example.webflux.firstwebflux.dto

import java.time.LocalDateTime

data class TodoResponse(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val created: LocalDateTime,
)