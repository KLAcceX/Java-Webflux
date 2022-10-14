package com.kl.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.kl.webflux.model.Book;

@Repository()
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
