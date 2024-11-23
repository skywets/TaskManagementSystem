package com.example.taskmanagementsystem.models.mappers;

import java.util.List;

public interface MapperDto <REQ,E, RESP>{
    RESP mapToDto(E entity);
    E mapToEntity(REQ dto);
    List<RESP> maptoDto(Iterable<E> entities);
    List<E> mapToEntity(Iterable<REQ> dtos);

}
