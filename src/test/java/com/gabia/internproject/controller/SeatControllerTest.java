package com.gabia.internproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.config.TestConfig;
import com.gabia.internproject.data.entity.*;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.request.SeatEmployeeUpdateRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.request.SeatUpdateRequestDTO;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.dto.response.RoleResponseDTO;
import com.gabia.internproject.dto.response.SeatResponseDTO;
import com.gabia.internproject.dto.response.SeatResponseDetailsDTO;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.OAuthConstants;
import com.gabia.internproject.service.SeatService;
import com.gabia.internproject.util.JwtTokenProvider;
import com.gabia.internproject.util.ObjectMaping.MapStruct.MapStructUtil;


import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.junit.*;
import org.junit.runner.RunWith;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(SeatController.class)
@Import(TestConfig.class)
public class SeatControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private SeatService seatService;


    private static ObjectMappingUtil mapper;

    private static ObjectMapper jsonMapper = new ObjectMapper();

    private static List<Floor> floorTable = new ArrayList<>();

    private List<Seat> seatTable = new ArrayList<>();

    private static List<Employee> employeeTable = new ArrayList<>();

    private Cookie jwt;

    @BeforeClass
    public static void init() throws Exception {

        mapper = new MapStructUtil();

        floorTableSettings();

        employeeTableSettings();


    }

    @Before
    public void createCookie() {
        JwtTokenProvider jwtTokenProvider2 = new JwtTokenProvider();
        EmployeeResponseDTO employee = new EmployeeResponseDTO();
        employee.setId(1);
        employee.setEmployeeName("test");
        RoleResponseDTO role = new RoleResponseDTO();
        role.setRoleType("ADMIN");
        employee.setRole(role);
        String jwtString = jwtTokenProvider.createToken(employee, OAuthAPIProvider.HIWORKS);
        jwt = new Cookie(OAuthConstants.SEAT_API_JWT.name(), jwtString);
    }

    private static void employeeTableSettings() {
        Employee employee1 = new Employee();
        employee1.setEmployeeName("익명1");
        employee1.setCompanyEmail("gabialeader.com");
        employee1.setEmployeeNumber("mm123");

        Employee employee2 = new Employee();
        employee2.setEmployeeName("익명2");
        employee2.setCompanyEmail("gabiamember.com");
        employee2.setEmployeeNumber("mm1234");
//        Department accountingDep= Department.createNewDepartment("회계");
//        Department devDep= Department.createNewDepartment("개발");
//
//        Role leader= Role.createNewRole("팀장");
//        Role member= Role.createNewRole("팀원");

        // Employee employee1= Employee.CreateNewEmployee("익명1","gabialeader.com","mm123");

//        employee1.setDepartment(accountingDep);
//        employee1.setRole(leader);

        //     Employee employee2=Employee.CreateNewEmployee("익명2","gabiamember.com","mm1234");
//        employee2.setDepartment(devDep);
//        employee2.setRole(member);

        employeeTable.add(employee1);
        employeeTable.add(employee2);
    }

    public static void floorTableSettings() {
        Floor first = Floor.createNewFloor("4층", "/4thFloor");
        Floor second = Floor.createNewFloor("5층", "/5thFloor");

        floorTable.add(first);
        floorTable.add(second);
    }


    public SeatResponseDetailsDTO convertToResponseDTO(Seat seat) {
        return mapper.convertToDTO(seat, SeatResponseDetailsDTO.class);
    }

    public List<SeatResponseDetailsDTO> convertToResponseDTO(List<Seat> seat) {
        return mapper.convertToDTO(seat, SeatResponseDetailsDTO.class);
    }

    public String convertToJson(SeatRequestDTO request) throws JsonProcessingException {

        return jsonMapper.writeValueAsString(request);
    }

    public String convertToJson(List<SeatUpdateRequestDTO> request) throws JsonProcessingException {

        return jsonMapper.writeValueAsString(request);
    }

    public String convertToJson(SeatEmployeeUpdateRequestDTO request) throws JsonProcessingException {

        return jsonMapper.writeValueAsString(request);
    }

    private int createSeatForTest(SeatRequestDTO seatRequestDTO) {
        Floor floor = findFloorById(seatRequestDTO.getFloorId());
        Seat seat = Seat.CreateNewSeat(floor, new Coordinate(seatRequestDTO.getCoordinate().getLat(), seatRequestDTO.getCoordinate().getLng()));
        seatTable.add(seat);
        return seatTable.size();
    }

    private Floor findFloorById(long floorId) {

        return floorTable.get((int) floorId - 1);
    }

    private Seat findSeatById(long seatId) {
        return seatTable.get((int) seatId - 1);
    }

    private Employee findEmployeeById(long employeeId) {
        return employeeTable.get((int) employeeId - 1);
    }

    public SeatEmployeeUpdateRequestDTO createSeatEmployeeUpdateRequestDTO(long employeeId) {

        SeatEmployeeUpdateRequestDTO request = new SeatEmployeeUpdateRequestDTO();
        request.setEmployeeId(employeeId);

        return request;
    }

    public SeatRequestDTO createSeatRequestDTO(long floorId, BigDecimal lat, BigDecimal lng) {
        SeatRequestDTO request = new SeatRequestDTO();
        request.setFloorId(floorId);


        CoordinateDTO cor = new CoordinateDTO();
        cor.setLat(lat);
        cor.setLng(lng);
        request.setCoordinate(cor);


        return request;
    }


    @Test
    public void getAllSeatsTestWithoutEmployee() throws Exception {

        final long firstFloorId = 1;
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);
        createSeatForTest(requestForCreate);

        final long secondFloorId = 2;
        final BigDecimal secondLat = new BigDecimal("11.225");
        final BigDecimal secondLng = new BigDecimal("-1.2544");
        requestForCreate = createSeatRequestDTO(secondFloorId, secondLat, secondLng);
        createSeatForTest(requestForCreate);


        given(seatService.getAllSeats()).willReturn(convertToResponseDTO(seatTable));


        this.mvc.perform(MockMvcRequestBuilders.get("/seats/employees").cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].coordinate.lat", containsInAnyOrder(12.852, 11.225)))
                .andExpect(jsonPath("$[0].coordinate.lat", is(12.852)))
                .andExpect(jsonPath("$[*].coordinate.lng", containsInAnyOrder(-1111.44, -1.2544)))
                .andExpect(jsonPath("$[0].coordinate.lng", is(-1111.44)))
                .andExpect(jsonPath("$[*].floor.floorName", containsInAnyOrder("4층", "5층")))
                .andExpect(jsonPath("$[*].floor.imagePath", containsInAnyOrder("/4thFloor", "/5thFloor")));


    }

    @Test
    public void createSeatWithValidParams() throws Exception {


        final long firstFloorId = 1;
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);

        Seat expectedEntity = findSeatById(createSeatForTest(requestForCreate));


        given(seatService.createSeat(ArgumentMatchers.any(SeatRequestDTO.class))).willReturn(convertToResponseDTO(expectedEntity));


        this.mvc.perform(MockMvcRequestBuilders.post("/seats").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForCreate))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.coordinate.lng", is(-1111.44)))
                .andExpect(jsonPath("$.floor.floorName", is("4층")))
                .andExpect(jsonPath("$.floor.imagePath", is("/4thFloor")));

    }


    @Test
    public void createSeatWithInvalidParams() throws Exception {

        createSeatWithInvalidFloorIdAndCoordination();

        createSeatWithInvalidCoordination();

        createSeatWithInvalidFloorId();

    }

    private void createSeatWithInvalidFloorIdAndCoordination() throws Exception {


        SeatRequestDTO request = new SeatRequestDTO();

        this.mvc.perform(MockMvcRequestBuilders.post("/seats").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorId)층 아이디를 입력하세요.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(coordinate)좌표값이 있어야 합니다.")));


    }

    private void createSeatWithInvalidCoordination() throws Exception {


        final long firstFloorId = 1;

        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, null, null);
        requestForCreate.setCoordinate(null);

        this.mvc.perform(MockMvcRequestBuilders.post("/seats").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForCreate))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString("(coordinate)좌표값이 있어야 합니다.")));


        requestForCreate = createSeatRequestDTO(firstFloorId, null, null);

        this.mvc.perform(MockMvcRequestBuilders.post("/seats").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForCreate))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason",
                        containsString("(coordinate.lat)위도를 입력하세요.")))
                .andExpect(jsonPath("$.reason",
                        containsString("(coordinate.lng)경도를 입력하세요.")));


    }

    private void createSeatWithInvalidFloorId() throws Exception {


        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(0, firstLat, firstLng);

        this.mvc.perform(MockMvcRequestBuilders.post("/seats").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForCreate))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorId)층 아이디를 입력하세요.")));


    }

    @Test
    public void getSeatWitValidSeatId() throws Exception {
        final long firstFloorId = 1;
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);


        long SeatId = createSeatForTest(requestForCreate);
        Seat expectedEntity = findSeatById(SeatId);


        given(seatService.getSeatById(ArgumentMatchers.eq(SeatId))).willReturn(convertToResponseDTO(expectedEntity));

        this.mvc.perform(MockMvcRequestBuilders.get("/seats/" + SeatId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.coordinate.lng", is(-1111.44)))
                .andExpect(jsonPath("$.coordinate.lat", is(12.852)))
                .andExpect(jsonPath("$.floor.floorName", is("4층")))
                .andExpect(jsonPath("$.floor.imagePath", is("/4thFloor")));


    }

    @Test
    public void getSeatWithInvalidSeatId() throws Exception {


        long seatId = 0;

        this.mvc.perform(MockMvcRequestBuilders.get("/seats/" + seatId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(getSeatById.seatId)자리 id 가 잘못되었습니다.")));
    }


    @Test
    public void updateSeatWithValidParams() throws Exception {

        final long firstFloorId = 1;
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);


        final long seatId = createSeatForTest(requestForCreate);
        Seat toBeUpdate = findSeatById(seatId);


        final long newFloorId = 2;
        SeatRequestDTO requestForUpdate = createSeatRequestDTO(newFloorId, lat, lng);
        toBeUpdate.setFloor(findFloorById(newFloorId));


        given(seatService.updateSeat(ArgumentMatchers.eq(seatId), ArgumentMatchers.any())).willReturn(convertToResponseDTO(toBeUpdate));

        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.coordinate.lng", is(-1111.44)))
                .andExpect(jsonPath("$.coordinate.lat", is(12.852)))
                .andExpect(jsonPath("$.floor.floorName", is("5층")))
                .andExpect(jsonPath("$.floor.imagePath", is("/5thFloor")));


    }

    @Test
    public void updateSeatInBulk() throws Exception {
        final long firstFloorId = 1;
        final BigDecimal lat1 = new BigDecimal("12.852");
        final BigDecimal lng1 = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat1, lng1);
        final long seatId1 = createSeatForTest(requestForCreate);

        final BigDecimal lat2 = new BigDecimal("11.852");
        final BigDecimal lng2 = new BigDecimal("-1211.44");
        requestForCreate = createSeatRequestDTO(firstFloorId, lat2, lng2);
        final long seatId2 = createSeatForTest(requestForCreate);


        final BigDecimal newLat1 = new BigDecimal("12.3");
        final BigDecimal newLng1 = new BigDecimal("14.23");

        final BigDecimal newLat2 = new BigDecimal("15.12");
        final BigDecimal newLng2 = new BigDecimal("16.25");


        findSeatById(seatId1).getCoordinate().setLat(newLat1);
        findSeatById(seatId1).getCoordinate().setLng(newLng1);

        SeatUpdateRequestDTO updateRequest1 = new SeatUpdateRequestDTO();
        updateRequest1.setId(seatId1);
        CoordinateDTO cor1 = new CoordinateDTO();
        cor1.setLat(newLat1);
        cor1.setLng(newLng1);
        updateRequest1.setCoordinate(cor1);
        updateRequest1.setFloorId(1);


        findSeatById(seatId2).getCoordinate().setLat(newLat2);
        findSeatById(seatId2).getCoordinate().setLng(newLng2);

        SeatUpdateRequestDTO updateRequest2 = new SeatUpdateRequestDTO();
        updateRequest2.setId(seatId2);
        CoordinateDTO cor2 = new CoordinateDTO();
        cor2.setLat(newLat2);
        cor2.setLng(newLng2);
        updateRequest2.setCoordinate(cor2);
        updateRequest2.setFloorId(1);

        List<Seat> expectedEntity = new ArrayList<>();
        expectedEntity.add(findSeatById(seatId1));
        expectedEntity.add(findSeatById(seatId2));

        List<SeatUpdateRequestDTO> requestForUpdate = new ArrayList<>();
        requestForUpdate.add(updateRequest1);
        requestForUpdate.add(updateRequest2);


        given(seatService.updateSeatInBulk(ArgumentMatchers.<SeatUpdateRequestDTO>anyList())).willReturn(convertToResponseDTO(expectedEntity));

        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/bulk").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].coordinate.lat", containsInAnyOrder(newLat1.doubleValue(), newLat2.doubleValue())))
                .andExpect(jsonPath("$[*].coordinate.lng", containsInAnyOrder(newLng1.doubleValue(), newLng2.doubleValue())));


    }


    @Test
    public void updateSeatWithInvalidParams() throws Exception {


        updateSeatWithInvalidFloorIdAndCoordination();
        updateSeatWithInvalidCoordination();
        updateSeatWithInvalidSeatId();


    }


    private void updateSeatWithInvalidFloorIdAndCoordination() throws Exception {

        SeatRequestDTO request = createSeatRequestDTO(0, null, null);
        request.setCoordinate(null);
        long seatId = 1;
        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorId)층 아이디를 입력하세요.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(coordinate)좌표값이 있어야 합니다.")));


    }


    private void updateSeatWithInvalidCoordination() throws Exception {

        final long seatId = 1;
        final long firstFloorId = 1;

        SeatRequestDTO requestForUpdate = createSeatRequestDTO(firstFloorId, null, null);
        requestForUpdate.setCoordinate(null);
        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(coordinate)좌표값이 있어야 합니다.")));


        requestForUpdate = createSeatRequestDTO(firstFloorId, null, null);


        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(coordinate.lat)위도를 입력하세요.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(coordinate.lng)경도를 입력하세요.")));

    }


    private void updateSeatWithInvalidSeatId() throws Exception {

        final long seatId = 0;
        final long firstFloorId = 1;
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForUpdate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);


        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(updateSeat.seatId)자리 id 가 잘못되었습니다.")));

    }


    @Test
    public void updateSeatWithEmployeeValidParams() throws Exception {

        final long firstFloorId = 1;
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);


        final long SeatId = createSeatForTest(requestForCreate);
        final long newEmployeeId = 2;

        SeatEmployeeUpdateRequestDTO requestUpdate = createSeatEmployeeUpdateRequestDTO(newEmployeeId);


        Seat toBeUpdate = findSeatById(SeatId);
        toBeUpdate.setEmployee(findEmployeeById(newEmployeeId));

        given(seatService.updateSeatWithEmployee(ArgumentMatchers.eq(SeatId),ArgumentMatchers.any(SeatEmployeeUpdateRequestDTO.class))).willReturn(convertToResponseDTO(toBeUpdate));

        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + SeatId + "/employee").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestUpdate))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.coordinate.lng", is(-1111.44)))
                .andExpect(jsonPath("$.coordinate.lat", is(12.852)))
                .andExpect(jsonPath("$.floor.floorName", is("4층")))
                .andExpect(jsonPath("$.floor.imagePath", is("/4thFloor")))
//                .andExpect(jsonPath("$.employee.departmentName", is("개발")))
//                .andExpect(jsonPath("$.employee.roleType", is("팀원")))
                .andExpect(jsonPath("$.employee.employeeName", is("익명2")));


    }

    @Test
    public void updateSeatWithEmployeeInvalidParams() throws Exception {

        updateSeatWithEmployeeWithInvalidSeatIdAndEmployeeId();
        updateSeatWithEmployeeWithInvalidSeatId();
        updateSeatWithEmployeeWithInvalidEmployeeId();


    }


    private void updateSeatWithEmployeeWithInvalidSeatIdAndEmployeeId() throws Exception {

        long seatId = 0;
        long newEmployeeId = 0;

        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId + "/employee").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "요청하신 데이터를 처리 할수 없습니다.")));


        SeatEmployeeUpdateRequestDTO request = createSeatEmployeeUpdateRequestDTO(newEmployeeId);


        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId + "/employee").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(employeeId)직원 id 가 잘못되었습니다.")));


    }

    private void updateSeatWithEmployeeWithInvalidSeatId() throws Exception {
        long seatId = 0;
        long newEmployeeId = 1;
        SeatEmployeeUpdateRequestDTO request = createSeatEmployeeUpdateRequestDTO(newEmployeeId);


        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId + "/employee").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(updateSeatWithEmployee.seatId)자리 id 가 잘못되었습니다.")));


    }

    private void updateSeatWithEmployeeWithInvalidEmployeeId() throws Exception {
        long seatId = 1;
        long newEmployeeId = 0;


        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId + "/employee").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "요청하신 데이터를 처리 할수 없습니다.")));


        SeatEmployeeUpdateRequestDTO request = createSeatEmployeeUpdateRequestDTO(newEmployeeId);


        this.mvc.perform(MockMvcRequestBuilders.patch("/seats/" + seatId + "/employee").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(employeeId)직원 id 가 잘못되었습니다.")));


    }

    @Test
    public void deleteSeatWithValidSeatId() throws Exception {

        long seatId = 1;


        this.mvc.perform(MockMvcRequestBuilders.delete("/seats/" + seatId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    @Test
    public void deleteSeatWithInvalidSeatId() throws Exception {

        long seatId = 0;


        this.mvc.perform(MockMvcRequestBuilders.delete("/seats/" + seatId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.reason", containsString(
                        "(deleteSeat.seatId)자리 id 가 잘못되었습니다.")));


    }


}

