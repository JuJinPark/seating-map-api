package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;



@Mapper(uses = {DepartmentMapper.class,RoleMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)

public abstract class EmployeeMapper implements MapStructMapper {

    public abstract EmployeeResponseDTO convertToDTO(Employee entity);

    public abstract Employee convertToEntity(EmployeeResponseDTO dto);

    abstract List<EmployeeResponseDTO> convertToDTO(Collection<Employee> entityList);

    abstract List<Employee> convertToEntity(Collection<EmployeeResponseDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){
        return (D)this.convertToDTO((Employee) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((EmployeeResponseDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Employee>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<EmployeeResponseDTO>) dtoList);
    };


}
