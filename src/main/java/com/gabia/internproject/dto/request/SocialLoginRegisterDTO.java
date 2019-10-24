package com.gabia.internproject.dto.request;


import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SocialLoginRegisterDTO {

    private String emailAddress;

    private long employeeId;

    private OAuthAPIProvider provider;

}