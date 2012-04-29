package com.services.mapper;

public interface Mapper<TInput, TOutput> {
public TOutput mapFrom(TInput input);
}
