package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.dto.request.FloorRequestDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class FloorMapper implements MapStructMapper {

    public abstract FloorResponseDTO convertToDTO(Floor source);

   public abstract Floor convertToEntity(FloorResponseDTO source);

    abstract List<FloorResponseDTO> convertToDTO(Collection<Floor> entityList);

    abstract List<Floor> convertToEntity(Collection<FloorResponseDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((Floor) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((FloorResponseDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Floor>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<FloorResponseDTO>) dtoList);
    };





}
