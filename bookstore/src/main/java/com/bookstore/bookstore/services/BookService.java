package com.bookstore.bookstore.services;

import com.bookstore.bookstore.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Pageable;

public interface BookService {
    BookEntity createUpdateBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findOne(String isbn);

    Boolean isExist(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void delete(String isbn);
}
