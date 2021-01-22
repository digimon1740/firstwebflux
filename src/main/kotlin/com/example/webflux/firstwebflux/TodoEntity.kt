package com.example.webflux.firstwebflux

import com.example.webflux.firstwebflux.dto.TodoResponse
import java.time.LocalDateTime

data class TodoEntity(
    val id: Int = 0,
    val todo: String,
    val completed: Boolean = false,
    val created: LocalDateTime = LocalDateTime.now(),
) {
    fun toResponse() =
        TodoResponse(id, todo, completed, created)
}
