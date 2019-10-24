package com.gabia.internproject.util.ObjectMaping.ModelMapper;


import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.modelmapper.ModelMapper;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperUtil implements ObjectMappingUtil{


    private static ModelMapper mapper;

    public ModelMapperUtil() {
        mapper = new ModelMapper();
    }


    @Override
    public <D, T>  D convertToDTO(T entity, Class<D> dtoClass) {

        return mapper.map(entity, dtoClass);
    }

    @Override
    public <D, T> List<D> convertToDTO(Collection<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> convertToDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }

    @Override
    public <D, T> D convertToEntity(T dto, Class<D> entityClass) {
        return mapper.map(dto, entityClass);
    }

    @Override
    public <D, T> List<D> convertToEntity(Collection<T> dtoList, Class<D> entityClass) {
        return dtoList.stream()
                .map(dto -> convertToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }


}
