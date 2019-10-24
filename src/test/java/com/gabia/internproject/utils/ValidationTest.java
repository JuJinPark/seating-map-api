package com.gabia.internproject.utils;

import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;


import static org.junit.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

public class ValidationTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSeatRequestDto() {
        // I'd name the test to something like
        // invalidEmailShouldFailValidation()

        SeatRequestDTO seatRequestDto = new SeatRequestDTO();
        seatRequestDto.setFloorId(1);
        CoordinateDTO cor= new CoordinateDTO();
        cor.setLng(new BigDecimal("12.2"));
        cor.setLat(new BigDecimal("12.2"));
        seatRequestDto.setCoordinate(cor);
        Set<ConstraintViolation<SeatRequestDTO>> violations = validator.validate(seatRequestDto);


//        Assert.assertThat(violations.size(), is(2));

        for(ConstraintViolation<SeatRequestDTO> constraintViolation : violations){

            System.out.println(constraintViolation.getMessage());
        }

      assertTrue(violations.isEmpty());
    }
}
