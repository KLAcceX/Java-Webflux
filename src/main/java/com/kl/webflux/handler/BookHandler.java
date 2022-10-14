package com.kl.webflux.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kl.webflux.model.Book;
import com.kl.webflux.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
@Slf4j
public class BookHandler {

    @Autowired
    private BookRepository bookRepository;

    public Mono<ServerResponse> getAllBooks(ServerRequest serverRequest) {
        log.info("Get All Books");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(bookRepository.findAll(), Book.class);
    }

    public Mono<ServerResponse> getBookById(ServerRequest serverRequest) {
        log.info("Get Book by Id");

        String bookId = serverRequest.pathVariable("bookId");
        log.debug(String.format("Get Book by Id: %s", bookId));

        if (bookId == null) {
            log.error("Invalid Book Id");
            return ServerResponse.badRequest().bodyValue("Invalid Book Id");
        }

        return bookRepository.findById(bookId)
                .flatMap(book -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(book)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createBook(ServerRequest serverRequest) {
        log.info("Create Book");
        Mono<Book> bookFromBody = serverRequest.bodyToMono(Book.class);
        log.debug(String.format("Create Book: %s", bookFromBody));
        return bookFromBody.flatMap(book -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON).body(bookRepository.save(book), Book.class));
    }

    public Mono<ServerResponse> updateBook(ServerRequest serverRequest) {
        log.info("Update Book");
        String bookId = serverRequest.pathVariable("bookId");
        log.debug(String.format("Update Book: %s", bookId));

        if (bookId == null) {
            log.error("Invalid Book Id");
            return ServerResponse.badRequest().bodyValue("Invalid Book Id");
        }

        return serverRequest
                .body(BodyExtractors.toMono(Book.class))
                .zipWith(bookRepository.findById(bookId)).flatMap((Tuple2<Book, Book> tuple) -> {
                    Book requisitionBook = tuple.getT1();
                    log.debug(String.format("Update Book (Requisition): %s", requisitionBook));
                    Book foundBook = tuple.getT2();
                    log.debug(String.format("Update Book (Database): %s", foundBook));
                    Book book = Book.builder()
                            .code(foundBook.getCode())
                            .name(requisitionBook.getName())
                            .author(requisitionBook.getAuthor())
                            .publisher(requisitionBook.getPublisher())
                            .build();
                    log.debug(String.format("Update Book (Created): %s", book));
                    return bookRepository.save(book);
                })
                .log()
                .flatMap(book -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(book, Book.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteBookById(ServerRequest serverRequest) {
        log.info("Get Book by Id");
        String bookId = serverRequest.pathVariable("bookId");
        log.debug(String.format("Get Book by Id: %s", bookId));

        if (bookId == null) {
            log.error("Invalid Book Id");
            return ServerResponse.badRequest().bodyValue("Invalid Book Id");
        }

        return bookRepository.findById(bookId)
                .flatMap(book -> bookRepository.delete(book)
                        .doOnEach(s -> log.debug(String.format("Deleting book: %s", book)))
                        .then(ServerResponse.ok().body(BodyInserters.fromValue(book))))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
