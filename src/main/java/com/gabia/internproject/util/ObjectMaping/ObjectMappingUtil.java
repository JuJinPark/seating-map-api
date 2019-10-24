package com.gabia.internproject.util.ObjectMaping;

import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.data.entity.Seat;
import com.gabia.internproject.dto.response.SeatResponseDTO;

import java.util.Collection;
import java.util.List;

public interface ObjectMappingUtil {

    <D, T> D convertToDTO(final T entity, Class<D> dtoClass);
    <D, T> List<D> convertToDTO(final Collection<T> entityList, Class<D> dtoClass);
    <D, T> D convertToEntity(final T dto, Class<D> entityClass);
    <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass);



}
