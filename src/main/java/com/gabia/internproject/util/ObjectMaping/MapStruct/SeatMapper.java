package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.data.entity.Seat;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.response.SeatResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {CoordinateMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SeatMapper implements MapStructMapper{

    public abstract SeatResponseDTO convertToDTO(Seat entity);

    public abstract Seat convertToEntity(SeatRequestDTO dto);

    abstract List<SeatResponseDTO> convertToDTO(Collection<Seat> entityList);

    abstract List<Seat> convertToEntity(Collection<SeatRequestDTO> dtoList);

    public <D, T> D convertToDTO(T entity,Class<D> dtoClass){
        return (D)this.convertToDTO((Seat) entity);
    };

    public <D, T> D convertToEntity(T dto,Class<D> entityClass){
        return (D)this.convertToEntity((SeatRequestDTO) dto);
    };

    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass){
        return (List<D>) this.convertToDTO( (Collection<Seat>) entityList);
    }

    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass){
        return (List<D>) this.convertToEntity( (Collection<SeatRequestDTO>) dtoList);
    };




}
