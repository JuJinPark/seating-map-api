package com.gabia.internproject.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FloorResponseDTO {

    @ApiModelProperty(value="층 아이디")
    private long id;
    @ApiModelProperty(value="층 이름")
    private String floorName;
    @ApiModelProperty(value="층 이미지 주소")
    private String imagePath;


}
