package com.example.webflux.firstwebflux

import com.example.webflux.firstwebflux.dto.TodoRequest
import com.example.webflux.firstwebflux.dto.TodoResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromProducer
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import java.net.URI

@Component
class TodoHandler(
    val repo: TodoRepository
) {

    fun getAll(request: ServerRequest): Mono<ServerResponse> {
        val response = repo.getAll().map(TodoEntity::toResponse)
        return ok().body(fromProducer(response, TodoResponse::class.java))
    }

    fun get(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id").toInt()
        return repo
            .get(id)
            .map(TodoEntity::toResponse)
            .flatMap { ok().body(fromValue(it)) }
            .switchIfEmpty(notFound().build())
    }

    fun write(request: ServerRequest): Mono<ServerResponse> {
        val bodyMono = request.bodyToMono(TodoRequest::class.java)
        return created(URI.create("/todos")).build(repo.write(bodyMono))
    }

    fun complete(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id").toInt()
        return ok().build(repo.complete(id))
    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id").toInt()
        return noContent().build(repo.delete(id))
    }
}

