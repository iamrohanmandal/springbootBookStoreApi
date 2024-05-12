package com.bookstore.bookstore.services.impl;

import com.bookstore.bookstore.domain.entities.BookEntity;
import com.bookstore.bookstore.repositories.BookRepository;
import com.bookstore.bookstore.services.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    @Override
    public Optional<BookEntity> findOne(String isbn) {
       return bookRepository.findById(isbn);
    }

    @Override
    public Boolean isExist(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
       return bookRepository.findById(isbn).map(existingBook -> {
        Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
        
        return bookRepository.save(existingBook);
       }).orElseThrow(() -> new RuntimeException("Author doesnot exists"));
       
    }
   
    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }

  

        
   


}
