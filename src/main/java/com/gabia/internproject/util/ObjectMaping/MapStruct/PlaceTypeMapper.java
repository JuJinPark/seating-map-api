package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.PlaceType;
import com.gabia.internproject.data.entity.Role;
import com.gabia.internproject.dto.response.PlaceTypeResponseDTO;
import com.gabia.internproject.dto.response.RoleResponseDTO;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class PlaceTypeMapper implements MapStructMapper  {


   abstract PlaceTypeResponseDTO convertToDTO(PlaceType entity);

    abstract PlaceType convertToEntity(PlaceTypeResponseDTO dto);

    abstract List<PlaceTypeResponseDTO> convertToDTO(Collection<PlaceType> entityList);

    abstract List<PlaceType> convertToEntity(Collection<PlaceTypeResponseDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((PlaceType) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((PlaceTypeResponseDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<PlaceType>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<PlaceTypeResponseDTO>) dtoList);
    };




}
