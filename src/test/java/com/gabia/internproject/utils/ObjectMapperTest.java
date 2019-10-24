package com.gabia.internproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.data.entity.*;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.dto.response.FloorResponseDetailsDTO;
import com.gabia.internproject.dto.response.SeatResponseDTO;

import com.gabia.internproject.dto.response.SeatResponseDetailsDTO;
import com.gabia.internproject.util.ObjectMaping.MapStruct.MapStructUtil;
import com.gabia.internproject.util.ObjectMaping.MapStruct.MapStructUtil2;
import com.gabia.internproject.util.ObjectMaping.ModelMapper.ModelMapperUtil;
import com.gabia.internproject.util.ObjectMaping.ModelMapper.ModelMapperUtil2;

import io.swagger.models.Model;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class ObjectMapperTest {

    private MapStructUtil mapper
            = new MapStructUtil();

    private ModelMapperUtil modelMapper = new ModelMapperUtil();

    @Test
    public void entityToDto() {

        Floor first = Floor.createNewFloor("4층", "/4th");
        Coordinate cor = new Coordinate(new BigDecimal("12.852"), new BigDecimal("-1111.44"));
        Coordinate cor2 = new Coordinate(new BigDecimal("121.852"), new BigDecimal("-11211.44"));
        List list = new ArrayList<>();
        list.add(cor);
        list.add(cor2);

//        Department accountingDep= new Department((long) 1,"회계");
//        Department devDep= new Department((long) 2,"개발");
//
//        Role leader= new Role((long)1,"팀장");
//        Role member= new Role((long)2,"팀원");
//
//        Employee test1=new Employee((long)1,accountingDep,leader,"익명1","gabialeader.com",123,"mm123",null,null,null,null,null);
//        Employee test2=new Employee((long)2,devDep,member,"익명2","gabiamember.com",1234,"mm1234",null,null,null,null,null);
        Seat seat = Seat.CreateNewSeat(first, cor);
//        Object s= (Object)first;
//        FloorDto dto = MapStructFactory.getMapper(FloorDto.class).convertToDto(s,FloorDto.class);

        SeatResponseDetailsDTO dto = mapper.convertToDTO(seat, SeatResponseDetailsDTO.class);
        Assert.assertThat(dto.getFloor().getFloorName(), is(first.getFloorName()));
        Assert.assertThat(dto.getFloor().getImagePath(), is(first.getImagePath()));

        Assert.assertThat(dto.getCoordinate().getLat(), is(cor.getLat()));
        Assert.assertThat(dto.getCoordinate().getLng(), is(cor.getLng()));

        List<CoordinateDTO> dtos = mapper.convertToDTO(list, CoordinateDTO.class);

        Assert.assertThat(dtos.get(0).getLat(), is(cor.getLat()));
        Assert.assertThat(dtos.get(0).getLng(), is(cor.getLng()));
        Assert.assertThat(dtos.get(1).getLat(), is(cor2.getLat()));
        Assert.assertThat(dtos.get(1).getLng(), is(cor2.getLng()));

    }

    @Test
    public void testFloorMapper() {

        Floor first = Floor.createNewFloor("4층", "/4th");
        Floor second = Floor.createNewFloor("5층", "/5th");

        Coordinate cor = new Coordinate(new BigDecimal("12.852"), new BigDecimal("-1111.44"));
        Coordinate cor2 = new Coordinate(new BigDecimal("121.852"), new BigDecimal("-11211.44"));

        Seat seat = Seat.CreateNewSeat(first, cor);
        Seat seat2 = Seat.CreateNewSeat(second, cor);

        first.addSeat(seat);
        second.addSeat(seat2);

        List<Floor> floorList = new ArrayList<>();
        floorList.add(first);
        floorList.add(second);
        List<FloorResponseDetailsDTO> dtos = mapper.convertToDTO(floorList, FloorResponseDetailsDTO.class);
        System.out.println(dtos.get(0).getSeats());

    }

    @Test
    public void testExtendModelMapper() throws JsonProcessingException {

        Floor first = Floor.createNewFloor("4층", "/4th");

        Coordinate cor = new Coordinate(new BigDecimal("12.852"), new BigDecimal("-1111.44"));
        Coordinate cor2 = new Coordinate(new BigDecimal("121.852"), new BigDecimal("-11211.44"));

        Seat seat = Seat.CreateNewSeat(first, cor);
        Seat seat2 = Seat.CreateNewSeat(first, cor2);

        Assert.assertThat(first.getSeats().size(), is(2));

        FloorResponseDetailsDTO dto = modelMapper.convertToDTO(first, FloorResponseDetailsDTO.class);
        ObjectMapper jsonMapper = new ObjectMapper();
        System.out.println(jsonMapper.writeValueAsString(dto));

        Assert.assertThat(dto.getFloorName(), is("4층"));
        Assert.assertThat(dto.getImagePath(), is("/4th"));
        Assert.assertThat(dto.getSeats().get(0).getCoordinate().getLat().doubleValue(), is(cor.getLat().doubleValue()));
        Assert.assertThat(dto.getSeats().get(1).getCoordinate().getLat().doubleValue(), is(cor2.getLat().doubleValue()));


    }

    @Test
    public void testExtendMapStructMapper() throws JsonProcessingException {

        Floor first = Floor.createNewFloor("4층", "/4th");

        Coordinate cor = new Coordinate(new BigDecimal("12.852"), new BigDecimal("-1111.44"));
        Coordinate cor2 = new Coordinate(new BigDecimal("121.852"), new BigDecimal("-11211.44"));

        Seat seat = Seat.CreateNewSeat(first, cor);
        Seat seat2 = Seat.CreateNewSeat(first, cor2);

        FloorResponseDetailsDTO dto = mapper.convertToDTO(first, FloorResponseDetailsDTO.class);
        SeatResponseDTO dtoSeat = mapper.convertToDTO(seat, SeatResponseDTO.class);

        ObjectMapper jsonMapper = new ObjectMapper();
        System.out.println(jsonMapper.writeValueAsString(dto));
        System.out.println(jsonMapper.writeValueAsString(dtoSeat));

        Assert.assertThat(dto.getFloorName(), is("4층"));
        Assert.assertThat(dto.getImagePath(), is("/4th"));
        Assert.assertThat(dto.getSeats().get(0).getCoordinate().getLat().doubleValue(), is(cor.getLat().doubleValue()));
        Assert.assertThat(dto.getSeats().get(1).getCoordinate().getLat().doubleValue(), is(cor2.getLat().doubleValue()));


    }


    @Test
    public void mapLibraryTest() {
        ModelMapperUtil2 modelMapper = new ModelMapperUtil2();
        MapStructUtil2 mapStructMapper = new MapStructUtil2();

        Coordinate cor = new Coordinate(new BigDecimal("12.852"), new BigDecimal("-1111.44"));

        CoordinateDTO coordinateDTO = modelMapper.covertToCoordinateDTO(cor);
        CoordinateDTO coordinateDTO2 = mapStructMapper.covertToCoordinateDTO(cor);

        Assert.assertThat(cor.getLng().doubleValue(), is(coordinateDTO.getLng().doubleValue()));
        Assert.assertThat(cor.getLng().doubleValue(), is(coordinateDTO2.getLng().doubleValue()));


    }

//    @Test
//    public void enumInterfaceTest(){
//
//        TestMapper modelTest= new mapperTestModel();
//        TestMapper mapStructTest= new mapperTestModel();
//
//        Entity testEn=new Entity("나","12");
//
//        EntityDTO dto=modelTest.ConvertoDto(testEn,)
//
//
//
//    }


}
