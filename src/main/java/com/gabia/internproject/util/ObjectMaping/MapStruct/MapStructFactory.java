package com.gabia.internproject.util.ObjectMaping.MapStruct;

import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.response.*;
import com.gabia.internproject.exception.customExceptions.MapperNotFoundException;
import org.mapstruct.factory.Mappers;

public class MapStructFactory {

    public static MapStructMapper getMapper(Class clazz) {

        if(FloorResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(FloorMapper.class );
        }
       else if(FloorResponseDetailsDTO.class.equals(clazz)){
            return Mappers.getMapper(FloorDetailMapper.class );
        }
        else if(SeatResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(SeatMapper.class );
        } else if(SeatResponseDetailsDTO.class.equals(clazz)){
            return Mappers.getMapper(SeatDetailMapper.class );
        }else if(EmployeeResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(EmployeeMapper.class );
        } else if(EmployeeResponseDetailsDTO.class.equals(clazz)){
            return Mappers.getMapper(EmployeeDetailMapper.class );
        }else if(PlaceResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(PlaceMapper.class );
        }else if(PlaceResponseDetailsDTO.class.equals(clazz)){
            return Mappers.getMapper(PlaceDetailsMapper.class );
        }else if(CoordinateDTO.class.equals(clazz)||(Coordinate.class.equals(clazz))){
            return Mappers.getMapper(CoordinateMapper.class );
        }else if(DepartmentResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(DepartmentMapper.class );
        }else if(RoleResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(RoleMapper.class );
        }else if(PlaceTypeResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(PlaceTypeMapper.class );
        }else if(SocialLoginEmailResponseDTO.class.equals(clazz)){
            return Mappers.getMapper(SocialLoginEmailMapper.class );
        }else if(SocialLoginEmailResponseDetailsDTO.class.equals(clazz)){
            return Mappers.getMapper(SocialLoginEmailDetailMapper.class );
        }
        else{
            throw new MapperNotFoundException();
        }


    }
}
