package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.data.entity.Role;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.response.RoleResponseDTO;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class RoleMapper implements MapStructMapper  {


   abstract RoleResponseDTO convertToDTO(Role entity);

    abstract Role convertToEntity(RoleResponseDTO dto);

    abstract List<RoleResponseDTO> convertToDTO(Collection<Role> entityList);

    abstract List<Role> convertToEntity(Collection<RoleResponseDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((Role) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((RoleResponseDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Role>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<RoleResponseDTO>) dtoList);
    };




}
