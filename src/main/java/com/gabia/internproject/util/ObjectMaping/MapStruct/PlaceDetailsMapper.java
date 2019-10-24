package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Place;
import com.gabia.internproject.data.entity.PlaceType;
import com.gabia.internproject.dto.response.PlaceResponseDTO;
import com.gabia.internproject.dto.response.PlaceResponseDetailsDTO;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;


@Mapper(uses = {CoordinateMapper.class,FloorMapper.class,PlaceTypeMapper.class})
public abstract class PlaceDetailsMapper implements MapStructMapper {


    public abstract PlaceResponseDetailsDTO convertToDTO(Place entity);

    public abstract Place convertToEntity(PlaceResponseDetailsDTO dto);

    abstract List<PlaceResponseDetailsDTO> convertToDTO(Collection<Place> entityList);

    abstract List<Place> convertToEntity(Collection<PlaceResponseDetailsDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){
        return (D)this.convertToDTO((Place) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((PlaceResponseDetailsDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Place>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<PlaceResponseDetailsDTO>) dtoList);
    };


}
