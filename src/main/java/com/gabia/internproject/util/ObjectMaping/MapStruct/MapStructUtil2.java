package com.gabia.internproject.util.ObjectMaping.MapStruct;

import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil2;
import org.mapstruct.factory.Mappers;

public class MapStructUtil2 implements ObjectMappingUtil2 {

    @Override
    public FloorResponseDTO convertToFloorResponseDTO(Floor floor) {
        return Mappers.getMapper(FloorMapper.class ).convertToDTO(floor);
    }

    @Override
    public EmployeeResponseDTO convertToEmployeeDTO(Employee employee) {
        return Mappers.getMapper(EmployeeMapper.class ).convertToDTO(employee);
    }

    @Override
    public CoordinateDTO covertToCoordinateDTO(Coordinate coordinate) {
        return Mappers.getMapper(CoordinateMapper.class ).convertToDTO(coordinate);
    }
}
