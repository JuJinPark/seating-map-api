package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.*;

import com.gabia.internproject.data.repository.SeatRepository;
import com.gabia.internproject.dto.request.SeatEmployeeUpdateRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.request.SeatUpdateRequestDTO;
import com.gabia.internproject.dto.response.SeatResponseDetailsDTO;
import com.gabia.internproject.exception.customExceptions.SeatNotFoundException;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {


    SeatRepository seatRepository;

    FloorService floorService;

    EmployeeService employeeService;

    ObjectMappingUtil mapper;

    @Autowired
    public SeatService(ObjectMappingUtil mapper, SeatRepository seatRepository) {
        this.mapper = mapper;
        this.seatRepository = seatRepository;
    }

    @Autowired
    public void setFloorService(FloorService floorService) {
                this.floorService = floorService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Transactional
    public List<SeatResponseDetailsDTO> getAllSeats() {

        return mapper.convertToDTO(seatRepository.findAll(), SeatResponseDetailsDTO.class);
    }

    @Transactional
    public SeatResponseDetailsDTO createSeat(SeatRequestDTO seatRequestDTO) {
        Floor floor = floorService.getFloorEntityById(seatRequestDTO.getFloorId());

        Coordinate coordinate = mapper.convertToEntity(seatRequestDTO.getCoordinate(), Coordinate.class);

        Seat newSeat = Seat.CreateNewSeat(floor, coordinate);

        return mapper.convertToDTO(seatRepository.save(newSeat), SeatResponseDetailsDTO.class);
    }

    @Transactional
    public SeatResponseDetailsDTO updateSeat(long seatId, SeatRequestDTO seatRequestDTO) {
        Seat seatToBeUpdate = getSeatEntityById(seatId);

        updateFloorIfChanged(seatToBeUpdate, seatRequestDTO.getFloorId());

        Coordinate newCoordinate = mapper.convertToEntity(seatRequestDTO.getCoordinate(), Coordinate.class);

        seatToBeUpdate.setCoordinate(newCoordinate);

        return mapper.convertToDTO(seatToBeUpdate, SeatResponseDetailsDTO.class);
    }

    @Transactional
    public List<SeatResponseDetailsDTO> updateSeatInBulk(List<SeatUpdateRequestDTO> list) {
        List<SeatResponseDetailsDTO> result = new ArrayList<>();
        for (SeatUpdateRequestDTO request : list) {
            SeatRequestDTO tmp = new SeatRequestDTO();
            tmp.setCoordinate(request.getCoordinate());
            tmp.setFloorId(request.getFloorId());
            result.add(updateSeat(request.getId(), tmp));
        }

        return result;


    }

    @Transactional
    public SeatResponseDetailsDTO updateSeatWithEmployee(long seatId, SeatEmployeeUpdateRequestDTO seatWithEmployeeDTO) {
        Seat seat = getSeatEntityById(seatId);
        Employee ownerOfSeat = employeeService.getEmployeeEntityById(seatWithEmployeeDTO.getEmployeeId());
        seat.assignTo(ownerOfSeat);
        return mapper.convertToDTO(seat, SeatResponseDetailsDTO.class);

    }

    @Transactional
    public SeatResponseDetailsDTO getSeatById(long seatId) {
        return mapper.convertToDTO(getSeatEntityById(seatId), SeatResponseDetailsDTO.class);
    }


    public void deleteSeat(long seatId) {
        seatRepository.deleteById(seatId);
    }

    public void deleteAllByFloorId(long floorId) {
        seatRepository.deleteAllByFloorIdInQuery(floorId);
    }

    private Seat getSeatEntityById(long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("requested seatId doesn't exist"));
        return seat;
    }

    private void updateFloorIfChanged(Seat seatToBeUpdate, long newPlaceId) {
        if (seatToBeUpdate.getFloor().getId() != newPlaceId) {
            Floor newFloor = floorService.getFloorEntityById(newPlaceId);
            seatToBeUpdate.updateFloor(newFloor);
        }
    }


}
