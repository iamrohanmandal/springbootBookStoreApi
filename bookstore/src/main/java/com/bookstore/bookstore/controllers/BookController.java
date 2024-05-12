package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.domain.dtos.BookDto;
import com.bookstore.bookstore.domain.entities.BookEntity;
import com.bookstore.bookstore.mapper.Mapper;
import com.bookstore.bookstore.services.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }


    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        Boolean bookExists = bookService.isExist(isbn);
            BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
            BookDto savedUpdateBookDto = bookMapper.mapTo(savedBookEntity);
        if(bookExists){
            // update
            return new ResponseEntity<>(savedUpdateBookDto, HttpStatus.OK);
        }
        else{
            
            return new ResponseEntity<>(savedUpdateBookDto, HttpStatus.CREATED);
        }
    
    }

    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable){
        Page<BookEntity> books = bookService.findAll(pageable);
        return books.map(bookMapper::mapTo);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn){
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.FOUND);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("books/{isbn}")
    public ResponseEntity<BookDto> partialUpdate(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        
        if(!bookService.isExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
        BookDto updateBookDto = bookMapper.mapTo(updatedBookEntity);
        return new ResponseEntity<>(updateBookDto, HttpStatus.OK);
    }

    @DeleteMapping("books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
