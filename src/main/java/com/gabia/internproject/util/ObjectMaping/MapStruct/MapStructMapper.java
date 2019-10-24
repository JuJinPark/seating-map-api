package com.gabia.internproject.util.ObjectMaping.MapStruct;

import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;

import java.util.Collection;
import java.util.List;

public interface MapStructMapper  {
    <D, T> D convertToDTO(T entity, Class<D> dtoClass) ;

    <D, T> D convertToEntity(T dto, Class<D> entityClass) ;

    <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass);

    <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass);



}
