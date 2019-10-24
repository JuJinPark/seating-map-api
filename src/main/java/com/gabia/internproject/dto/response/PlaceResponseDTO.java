package com.gabia.internproject.dto.response;



import com.gabia.internproject.dto.CoordinateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
public class PlaceResponseDTO {
    @ApiModelProperty(value="장소 아이디")
    private long id;
    @ApiModelProperty(value="장소 이름")
    private String placeName;
    @ApiModelProperty(value="좌표")
    private CoordinateDTO coordinate;


}
