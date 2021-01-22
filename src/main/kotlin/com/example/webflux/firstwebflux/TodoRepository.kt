package com.example.webflux.firstwebflux

import com.example.webflux.firstwebflux.dto.TodoRequest
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class TodoRepository {

    val idGenerator = AtomicInteger()
    val data = ConcurrentHashMap<Int, TodoEntity>()

    fun getAll(): Flux<TodoEntity> =
        data.values
            .toList()
            .sortedByDescending(TodoEntity::created)
            .toFlux()

    fun get(id: Int): Mono<TodoEntity> =
        data[id]?.let { Mono.just(it) } ?: Mono.empty()

    fun write(todoRequest: Mono<TodoRequest>): Mono<Void> =
        todoRequest
            .flatMap {
                val id = idGenerator.incrementAndGet()
                data[id] = TodoEntity(id = id, it.todo)
                it.toMono()
            }.then()

    fun complete(id: Int): Mono<Void> =
        get(id)
            .flatMap {
                data[id] = it.copy(completed = true)
                true.toMono()
            }.then()

    fun delete(id: Int): Mono<Void> =
        get(id)
            .flatMap {
                data.remove(id)
                true.toMono()
            }.then()


}
