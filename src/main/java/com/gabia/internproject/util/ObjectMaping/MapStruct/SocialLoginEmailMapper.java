package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Role;
import com.gabia.internproject.data.entity.SocialLoginEmail;
import com.gabia.internproject.dto.response.RoleResponseDTO;
import com.gabia.internproject.dto.response.SocialLoginEmailResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SocialLoginEmailMapper implements MapStructMapper  {


   abstract SocialLoginEmailResponseDTO convertToDTO(SocialLoginEmail entity);

    abstract SocialLoginEmail convertToEntity(SocialLoginEmailResponseDTO dto);

    abstract List<SocialLoginEmailResponseDTO> convertToDTO(Collection<SocialLoginEmail> entityList);

    abstract List<SocialLoginEmail> convertToEntity(Collection<SocialLoginEmailResponseDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((SocialLoginEmail) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((SocialLoginEmailResponseDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<SocialLoginEmail>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<SocialLoginEmailResponseDTO>) dtoList);
    };




}
