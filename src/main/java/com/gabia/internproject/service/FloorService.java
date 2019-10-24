package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.data.repository.FloorRepository;
import com.gabia.internproject.dto.request.FloorRequestDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.dto.response.FloorResponseDetailsDTO;
import com.gabia.internproject.exception.customExceptions.FloorNotFoundException;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FloorService {

    FloorRepository floorRepository;

    ObjectMappingUtil mapper;

    SeatService seatService;

    PlaceService placeService;

    @Autowired
    public FloorService(ObjectMappingUtil mapper,FloorRepository floorRepository){
        this.mapper=mapper;
        this.floorRepository=floorRepository;
    }


    @Autowired
    public void setSeatService(SeatService seatService) {
        this.seatService = seatService;
    }

    @Autowired
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Transactional
    public List<FloorResponseDTO> getAllFloors() {
        return mapper.convertToDTO(floorRepository.findAll(),FloorResponseDTO.class);

    }

    @Transactional
    public FloorResponseDTO createFloor(FloorRequestDTO floorRequestDTO){

        Floor newFloor=Floor.createNewFloor(floorRequestDTO.getFloorName(),floorRequestDTO.getImagePath());

        return mapper.convertToDTO(floorRepository.save(newFloor),FloorResponseDTO.class);

    }

    @Transactional
    public FloorResponseDetailsDTO getFloorById(long floorId){
        return mapper.convertToDTO(getFloorEntityById(floorId), FloorResponseDetailsDTO.class);
    }


    @Transactional
    public FloorResponseDetailsDTO updateFloor(long floorId, FloorRequestDTO floorRequestDTO){
        Floor floorToBeUpdate=getFloorEntityById(floorId);

        floorToBeUpdate.setFloorName(floorRequestDTO.getFloorName());
        floorToBeUpdate.setImagePath(floorRequestDTO.getImagePath());

        return mapper.convertToDTO(floorToBeUpdate, FloorResponseDetailsDTO.class);
    }

    @Transactional
    public void deleteFloor(long floorId){
        seatService.deleteAllByFloorId(floorId);
        placeService.deleteAllByFloorId(floorId);
        floorRepository.deleteById(floorId);
    }



    public Floor getFloorEntityById(long floorID){

        Floor floor = floorRepository.findById(floorID)
                        .orElseThrow(()->new FloorNotFoundException("requested floorId doesn't exist"));

        return floor;
    }

//    public Boolean isFloorChanged(Floor oldOne,long newOneId){
//        return oldOne.getId()!=newOneId;
//    }
//

}
