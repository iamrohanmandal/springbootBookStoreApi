package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.domain.entities.AuthorEntity;
import com.bookstore.bookstore.mapper.Mapper;
import org.springframework.web.bind.annotation.*;

import com.bookstore.bookstore.domain.dtos.AuthorDto;
import com.bookstore.bookstore.services.AuthorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }
    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream().map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto>  getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.FOUND);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PutMapping("authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {

        if(!authorService.isExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.OK);


    }

    @PatchMapping("authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedUpdatedAuthorEntity = authorService.partialUpdate(id,authorEntity);
        AuthorDto savedAuthorDto = authorMapper.mapTo(savedUpdatedAuthorEntity);
        return new ResponseEntity<>(savedAuthorDto, HttpStatus.OK);

    }

    @DeleteMapping("authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}