package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Place;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.dto.response.PlaceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;


@Mapper(uses = {CoordinateMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PlaceMapper implements MapStructMapper {


    public abstract PlaceResponseDTO convertToDTO(Place entity);

    public abstract Place convertToEntity(PlaceResponseDTO dto);

    abstract List<PlaceResponseDTO> convertToDTO(Collection<Employee> entityList);

    abstract List<Place> convertToEntity(Collection<PlaceResponseDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){
        return (D)this.convertToDTO((Place) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((PlaceResponseDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Employee>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<PlaceResponseDTO>) dtoList);
    };


}
