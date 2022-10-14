package com.kl.webflux.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kl.webflux.handler.BookHandler;

@Configuration
public class BookRouterConfig {

        @Bean
        RouterFunction<ServerResponse> routes(BookHandler bookHandler) {
                return RouterFunctions
                                .route(RequestPredicates.GET("/books")
                                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                                bookHandler::getAllBooks)
                                .andRoute(RequestPredicates.POST("/books")
                                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                                bookHandler::createBook)
                                .andRoute(RequestPredicates.GET("/books/{bookId}")
                                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                                bookHandler::getBookById)
                                .andRoute(RequestPredicates.PUT("/books/{bookId}")
                                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                                bookHandler::updateBook)
                                .andRoute(RequestPredicates.DELETE("/books/{bookId}")
                                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                                                bookHandler::deleteBookById);
        }
}
