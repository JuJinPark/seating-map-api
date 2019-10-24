package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.PlaceType;
import com.gabia.internproject.data.repository.PlaceTypeRepository;
import com.gabia.internproject.exception.customExceptions.PlaceTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceTypeService {
    PlaceTypeRepository placeTypeRepository;

    @Autowired
    public PlaceTypeService(PlaceTypeRepository placeTypeRepository){

        this.placeTypeRepository=placeTypeRepository;
    }

    public PlaceType getPlaceTypeEntityById(long placeTypeId){
        PlaceType placeType = placeTypeRepository.findById(placeTypeId)
                .orElseThrow(()->new PlaceTypeNotFoundException("requested placeTypeId doesn't exist"));

        return placeType;
    }

//    public Boolean isPlaceTypeChanged(PlaceType oldOne,long newOneId){
//        return oldOne.getId()!=newOneId;
//    }

}
