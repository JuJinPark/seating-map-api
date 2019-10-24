package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.SocialLoginEmail;
import com.gabia.internproject.dto.response.SocialLoginEmailResponseDTO;
import com.gabia.internproject.dto.response.SocialLoginEmailResponseDetailsDTO;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;


@Mapper(uses = {EmployeeMapper.class})
public abstract class SocialLoginEmailDetailMapper implements MapStructMapper  {


   abstract SocialLoginEmailResponseDetailsDTO convertToDTO(SocialLoginEmail entity);

    abstract SocialLoginEmail convertToEntity(SocialLoginEmailResponseDetailsDTO dto);

    abstract List<SocialLoginEmailResponseDetailsDTO> convertToDTO(Collection<SocialLoginEmail> entityList);

    abstract List<SocialLoginEmail> convertToEntity(Collection<SocialLoginEmailResponseDetailsDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((SocialLoginEmail) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((SocialLoginEmailResponseDetailsDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<SocialLoginEmail>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<SocialLoginEmailResponseDetailsDTO>) dtoList);
    };




}
