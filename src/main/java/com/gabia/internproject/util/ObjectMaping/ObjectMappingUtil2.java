package com.gabia.internproject.util.ObjectMaping;

import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;

public interface ObjectMappingUtil2 {

    FloorResponseDTO convertToFloorResponseDTO(Floor floor);

    EmployeeResponseDTO convertToEmployeeDTO(Employee employee);

    CoordinateDTO covertToCoordinateDTO(Coordinate coordinate);



}
