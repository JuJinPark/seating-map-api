package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.dto.CoordinateDTO;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class CoordinateMapper implements MapStructMapper  {



   abstract CoordinateDTO convertToDTO(Coordinate entity);

    abstract Coordinate convertToEntity(CoordinateDTO dto);

    abstract List<CoordinateDTO> convertToDTO(Collection<Coordinate> entityList);

    abstract List<Coordinate> convertToEntity(Collection<CoordinateDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((Coordinate) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((CoordinateDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Coordinate>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<CoordinateDTO>) dtoList);
    };




}
