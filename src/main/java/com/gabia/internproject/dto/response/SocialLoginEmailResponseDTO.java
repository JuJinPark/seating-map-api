package com.gabia.internproject.dto.response;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SocialLoginEmailResponseDTO {

    private long id;
    private String emailAddress;
    private String provider;

}
