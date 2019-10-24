package com.gabia.internproject.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PlaceTypeResponseDTO {
    @ApiModelProperty(value="장소타입 아이디")
    private long id;
    @ApiModelProperty(value="장소타입 이름")
    private String typeName;
}
