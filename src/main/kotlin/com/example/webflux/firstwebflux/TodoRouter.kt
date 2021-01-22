package com.example.webflux.firstwebflux

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class TodoRouter {

    @Bean
    fun routes(handler: TodoHandler) = router {
        "/todos".nest {
            listOf(
                GET("", handler::getAll),
                GET("/{id}", handler::get),
                POST("", handler::write),
                PUT("/{id}/completion", handler::complete),
                DELETE("/{id}", handler::delete),
            )
        }
    }
}