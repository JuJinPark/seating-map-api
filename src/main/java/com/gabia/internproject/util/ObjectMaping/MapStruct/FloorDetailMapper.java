package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.dto.response.FloorResponseDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {SeatMapper.class,PlaceMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class FloorDetailMapper implements MapStructMapper {

    public abstract FloorResponseDetailsDTO convertToDTO(Floor source);

   public abstract Floor convertToEntity(FloorResponseDetailsDTO source);

    abstract List<FloorResponseDetailsDTO> convertToDTO(Collection<Floor> entityList);

    abstract List<Floor> convertToEntity(Collection<FloorResponseDetailsDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){

        return (D)this.convertToDTO((Floor) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((FloorResponseDetailsDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Floor>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<FloorResponseDetailsDTO>) dtoList);
    };





}
