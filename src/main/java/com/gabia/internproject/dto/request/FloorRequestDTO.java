package com.gabia.internproject.dto.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FloorRequestDTO {
    @NotBlank(message = "층 이름이 있어야 합니다.")
    @ApiModelProperty(value="층 이름")
    private String floorName;
    @NotBlank(message = "층 이미지 주소가 있어야 합니다.")
    @ApiModelProperty(value="층 이미지 주소")
    private String imagePath;

}
