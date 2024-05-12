package com.bookstore.bookstore.mapper;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);

}
