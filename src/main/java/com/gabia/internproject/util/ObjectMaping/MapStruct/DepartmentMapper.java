package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Department;
import com.gabia.internproject.data.entity.Role;
import com.gabia.internproject.dto.response.DepartmentResponseDTO;
import com.gabia.internproject.dto.response.RoleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DepartmentMapper implements MapStructMapper  {


   abstract DepartmentResponseDTO convertToDTO(Department entity);

    abstract Department convertToEntity(DepartmentResponseDTO dto);

    abstract List<DepartmentResponseDTO> convertToDTO(Collection<Department> entityList);

    abstract List<Role> convertToEntity(Collection<DepartmentResponseDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((Department) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((DepartmentResponseDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Department>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<DepartmentResponseDTO>) dtoList);
    };




}
