package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.data.entity.Place;
import com.gabia.internproject.data.entity.PlaceType;
import com.gabia.internproject.data.repository.PlaceRepository;

import com.gabia.internproject.dto.request.PlaceRequestDTO;
import com.gabia.internproject.dto.response.PlaceResponseDetailsDTO;
import com.gabia.internproject.exception.customExceptions.PlaceNotFoundException;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceService {

    PlaceRepository placeRepository;

    ObjectMappingUtil mapper;

    FloorService floorService;

    PlaceTypeService placeTypeService;

    @Autowired
    public PlaceService(ObjectMappingUtil mapper, PlaceRepository placeRepository){
        this.mapper=mapper;
        this.placeRepository=placeRepository;
    }

    @Autowired
    public void setFloorService(FloorService floorService) {
        this.floorService = floorService;
    }

    @Autowired
    public void setEmployeeService(PlaceTypeService placeTypeService) {
        this.placeTypeService = placeTypeService;
    }


    @Transactional
    public PlaceResponseDetailsDTO createPlace(PlaceRequestDTO placeRequestDTO){

        Floor floor= floorService.getFloorEntityById(placeRequestDTO.getFloorId());
        PlaceType placeType=placeTypeService.getPlaceTypeEntityById(placeRequestDTO.getPlaceTypeId());
        Coordinate coordinate=mapper.convertToEntity(placeRequestDTO.getCoordinate(),Coordinate.class);

        Place newPlace= Place.CreateNewPlace(floor,placeType,placeRequestDTO.getPlaceName(),coordinate);

        return mapper.convertToDTO(placeRepository.save(newPlace), PlaceResponseDetailsDTO.class);
    }

    @Transactional
    public List<PlaceResponseDetailsDTO> getAllPlaces() {

        return mapper.convertToDTO(placeRepository.findAll(), PlaceResponseDetailsDTO.class);
    }

    @Transactional
    public PlaceResponseDetailsDTO getPlaceById(long placeId){
        return mapper.convertToDTO(getPlaceEntityById(placeId), PlaceResponseDetailsDTO.class);
    }

    @Transactional
    public PlaceResponseDetailsDTO updatePlace(long placeId, PlaceRequestDTO placeRequestDTO){
        Place placeToBeUpdate=getPlaceEntityById(placeId);

        updateFloorIfChanged(placeToBeUpdate,placeRequestDTO.getFloorId());

        updatePlaceTypeIfChanged(placeToBeUpdate,placeRequestDTO.getPlaceTypeId());

        placeToBeUpdate.setPlaceName(placeRequestDTO.getPlaceName());

        Coordinate newCoordinate=mapper.convertToEntity(placeRequestDTO.getCoordinate(),Coordinate.class);

        placeToBeUpdate.setCoordinate(newCoordinate);

        return mapper.convertToDTO(placeToBeUpdate, PlaceResponseDetailsDTO.class);
    }

    @Transactional
    public void deletePlace(long placeId){

        placeRepository.deleteById(placeId);
    }

    private void updateFloorIfChanged(Place placeToBeUpdate,long newPlaceId) {
        if (placeToBeUpdate.getFloor().getId() != newPlaceId) {
            Floor newFloor = floorService.getFloorEntityById(newPlaceId);
            placeToBeUpdate.updateFloor(newFloor);
        }
    }

     private void updatePlaceTypeIfChanged(Place placeToBeUpdate,long newPlaceTypeId) {
        if(placeToBeUpdate.getPlaceType().getId()!=newPlaceTypeId){
            PlaceType newPlaceType = placeTypeService.getPlaceTypeEntityById(newPlaceTypeId);
            placeToBeUpdate.setPlaceType(newPlaceType);
        }

        }


    private Place getPlaceEntityById(long placeId){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(()->new PlaceNotFoundException("requested placeId doesn't exist"));
        return place;
    }

    public void deleteAllByFloorId(long floorId){
        placeRepository.deleteAllByFloorIdInQuery(floorId);
    }

}
