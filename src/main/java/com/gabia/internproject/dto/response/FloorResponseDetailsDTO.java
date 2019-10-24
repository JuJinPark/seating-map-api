package com.gabia.internproject.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class FloorResponseDetailsDTO extends FloorResponseDTO {
    @ApiModelProperty(value="자리 리스트")
    private List<SeatResponseDTO> seats;
    @ApiModelProperty(value="장소 리스트")
    private List<PlaceResponseDTO> places;

}
