package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.SocialLoginEmail;
import com.gabia.internproject.data.repository.SocialLoginEmailRepository;
import com.gabia.internproject.dto.request.SocialLoginRegisterDTO;
import com.gabia.internproject.dto.response.SocialLoginEmailResponseDTO;
import com.gabia.internproject.dto.response.SocialLoginEmailResponseDetailsDTO;
import com.gabia.internproject.exception.customExceptions.RegisteredEmailException;
import com.gabia.internproject.exception.customExceptions.SocialEmailNotFoundException;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SocialLoginEmailService {

    SocialLoginEmailRepository socialLoginEmailRepository;

    ObjectMappingUtil mapper;

    EmployeeService employeeService;

    @Autowired
    public SocialLoginEmailService(ObjectMappingUtil mapper, SocialLoginEmailRepository socialLoginEmailRepository){
        this.mapper=mapper;
        this.socialLoginEmailRepository=socialLoginEmailRepository;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Transactional
    public SocialLoginEmailResponseDetailsDTO registerSocialEmail(SocialLoginRegisterDTO requestDTO){

        SocialLoginEmail newEmail= new SocialLoginEmail();

       if(isDuplicateEmail(requestDTO.getEmailAddress(),requestDTO.getProvider())){
            throw new RegisteredEmailException("requested email is already registered");
        }

        Employee employee=employeeService.getEmployeeEntityById(requestDTO.getEmployeeId());
        newEmail.setEmployee(employee);
        newEmail.setEmailAddress(requestDTO.getEmailAddress());
        newEmail.setProvider(requestDTO.getProvider());

        return mapper.convertToDTO(socialLoginEmailRepository.save(newEmail), SocialLoginEmailResponseDetailsDTO.class);
    }

    private boolean isDuplicateEmail(String emailAddress, OAuthAPIProvider provider){
        long emailCount= socialLoginEmailRepository.countByEmailAddressAndProvider(emailAddress,provider);
        if(emailCount>0){
            return true;
        }
        return false;
    }

    @Transactional
    public SocialLoginEmailResponseDTO getSocialLoginEmailById(long id){
       return mapper.convertToDTO(getSocialLoginEmailEntityById(id),SocialLoginEmailResponseDTO.class);
    };

    @Transactional
    public SocialLoginEmailResponseDetailsDTO getSocialLoginEmailByEmailAndProvider(String emailAddress,OAuthAPIProvider provider){
        SocialLoginEmailResponseDetailsDTO ss=mapper.convertToDTO(getSocialLoginEmailEntityByAddressAndProvider(emailAddress,provider),SocialLoginEmailResponseDetailsDTO.class);
        return ss;
        };


    private SocialLoginEmail getSocialLoginEmailEntityByAddressAndProvider(String emailAddress,OAuthAPIProvider provider){
        SocialLoginEmail email=socialLoginEmailRepository.findByEmailAddressAndProvider(emailAddress,provider)
                                .orElseThrow(()-> new SocialEmailNotFoundException("requested SocialLoginEmail doesn't exist") );
        return email;
    }

    private SocialLoginEmail getSocialLoginEmailEntityById(long id){
        SocialLoginEmail email=socialLoginEmailRepository.findById(id)
                .orElseThrow(()-> new SocialEmailNotFoundException("requested SocialLoginEmail Id doesn't exist") );
        return email;
    }











}
