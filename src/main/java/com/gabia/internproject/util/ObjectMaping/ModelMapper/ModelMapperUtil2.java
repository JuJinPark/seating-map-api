package com.gabia.internproject.util.ObjectMaping.ModelMapper;


import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil2;
import org.modelmapper.ModelMapper;

public class ModelMapperUtil2 implements ObjectMappingUtil2 {


    private static ModelMapper mapper;

    public ModelMapperUtil2() {
        mapper = new ModelMapper();
    }


    public FloorResponseDTO convertToFloorResponseDTO(Floor floor){

       return mapper.map(floor,FloorResponseDTO.class);
    };

    public EmployeeResponseDTO convertToEmployeeDTO(Employee employee){
       return mapper.map(employee, EmployeeResponseDTO.class);

    };

    public CoordinateDTO covertToCoordinateDTO(Coordinate coordinate){
        return mapper.map(coordinate,CoordinateDTO.class);
    };



}
