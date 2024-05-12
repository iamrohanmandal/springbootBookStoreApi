package com.bookstore.bookstore.mapper.impl;

import com.bookstore.bookstore.domain.dtos.AuthorDto;
import com.bookstore.bookstore.domain.entities.AuthorEntity;
import com.bookstore.bookstore.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {
    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);

    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {

        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
