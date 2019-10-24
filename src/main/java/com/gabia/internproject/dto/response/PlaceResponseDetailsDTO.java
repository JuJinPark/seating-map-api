package com.gabia.internproject.dto.response;



import com.gabia.internproject.dto.CoordinateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Required;

@Getter
@Setter
public class PlaceResponseDetailsDTO extends PlaceResponseDTO {

    @ApiModelProperty(value="층")
    private FloorResponseDTO floor;
    @ApiModelProperty(value="장소")
    private PlaceTypeResponseDTO placeType;



}
