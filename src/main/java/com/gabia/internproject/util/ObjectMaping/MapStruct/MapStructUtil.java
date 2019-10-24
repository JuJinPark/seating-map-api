package com.gabia.internproject.util.ObjectMaping.MapStruct;


import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class MapStructUtil implements ObjectMappingUtil {

    @Override
    public <D, T>  D convertToDTO(T entity , Class<D> dtoClass) {

        return MapStructFactory.getMapper(dtoClass).convertToDTO(entity,dtoClass);
    }

    @Override
    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass) {
        return MapStructFactory.getMapper(dtoClass).convertToDTO(entityList,dtoClass);
    }

    @Override
    public <D, T> D convertToEntity(T dto, Class<D> entityClass) {

        return MapStructFactory.getMapper(entityClass).convertToEntity(dto,entityClass);
    }

    @Override
    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass) {
        return MapStructFactory.getMapper(entityClass).convertToEntity(dtoList,entityClass);
    }


}
