package com.gabia.internproject.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDTO {
    @ApiModelProperty(value="권한 아이디")
    private long id;
    @ApiModelProperty(value="권한 종류")
    private String roleType;
}
