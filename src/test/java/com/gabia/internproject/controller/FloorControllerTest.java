package com.gabia.internproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.config.TestConfig;
import com.gabia.internproject.data.entity.Coordinate;
import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.data.entity.Seat;
import com.gabia.internproject.dto.request.FloorRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.response.*;
import com.gabia.internproject.service.FloorService;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.OAuthConstants;
import com.gabia.internproject.util.JwtTokenProvider;
import com.gabia.internproject.util.ObjectMaping.MapStruct.MapStructUtil;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FloorController.class)
@Import(TestConfig.class)
public class FloorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private FloorService floorService;

    private List<Floor> floorTable = new ArrayList<>();

    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static ObjectMappingUtil mapper;
    private Cookie jwt;

    @BeforeClass
    public static void init() throws Exception {

        mapper = new MapStructUtil();

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
//    public FloorResponseDTO convertToResponseDTO(Floor floor){
//        return mapper.convertToDTO(floor, FloorResponseDTO.class);
//    }

    public <D> D convertToResponseDTO(Floor floor, Class<D> dtoClass) {
        return mapper.convertToDTO(floor, dtoClass);
    }

    public <D> List<D> convertToResponseDTO(List<Floor> floor, Class<D> dtoClass) {
        return mapper.convertToDTO(floor, dtoClass);
    }

    public FloorRequestDTO createFloorRequestDTO(String floorName, String imagePath) {
        FloorRequestDTO request = new FloorRequestDTO();
        request.setFloorName(floorName);
        request.setImagePath(imagePath);
        return request;

    }

    public Floor createFloorForTest(FloorRequestDTO request) {
        Floor floor = Floor.createNewFloor(request.getFloorName(), request.getImagePath());

        floorTable.add(floor);
        return floor;
    }

    public Seat createSeatForTest(Floor floor) {
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        Coordinate cor = new Coordinate();
        cor.setLat(lat);
        cor.setLng(lng);
        return Seat.CreateNewSeat(floor, cor);
    }

    public String convertToJson(FloorRequestDTO request) throws JsonProcessingException {

        return jsonMapper.writeValueAsString(request);
    }

    private Floor findFloorById(long floorId) {

        return floorTable.get((int) floorId - 1);
    }

    @Test
    public void getAllFloors() throws Exception {
        final String floorName1 = "4층";
        final String imagePath1 = "/4thFloor";
        FloorRequestDTO request = createFloorRequestDTO(floorName1, imagePath1);
        Floor firstFloor = createFloorForTest(request);

        final String floorName2 = "5층";
        final String imagePath2 = "/5thFloor";
        request = createFloorRequestDTO(floorName2, imagePath2);
        Floor secondFloor = createFloorForTest(request);


        List<FloorResponseDTO> dto = convertToResponseDTO(floorTable, FloorResponseDTO.class);
        given(floorService.getAllFloors()).willReturn(convertToResponseDTO(floorTable, FloorResponseDTO.class));


        this.mvc.perform(MockMvcRequestBuilders.get("/floors").cookie(jwt).characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].floorName", containsInAnyOrder(floorName1, floorName2)))
                .andExpect(jsonPath("$[*].imagePath", containsInAnyOrder(imagePath1, imagePath2)));


    }

    @Test
    public void createFloor() throws Exception {

        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        FloorRequestDTO request = createFloorRequestDTO(floorName, imagePath);
        Floor firstFloor = createFloorForTest(request);

        given(floorService.createFloor(ArgumentMatchers.any(FloorRequestDTO.class))).willReturn(convertToResponseDTO(firstFloor, FloorResponseDTO.class));


        this.mvc.perform(MockMvcRequestBuilders.post("/floors").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)).characterEncoding("utf-8")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.floorName", is(floorName)))
                .andExpect(jsonPath("$.imagePath", is(imagePath)));


    }

    @Test
    public void createFloorWithInvalidParams() throws Exception {

        createFloorWithInvalidFloorNameAndImagePath();

        createSeatWithInvalidFloorName();

        createSeatWithInvalidImagePath();

    }

    private void createFloorWithInvalidFloorNameAndImagePath() throws Exception {

        final String floorName = "";
        final String imagePath = " ";
        FloorRequestDTO request = createFloorRequestDTO(floorName, imagePath);

        this.mvc.perform(MockMvcRequestBuilders.post("/floors").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)).characterEncoding("utf-8")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorName)층 이름이 있어야 합니다.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(imagePath)층 이미지 주소가 있어야 합니다.")));


    }

    private void createSeatWithInvalidFloorName() throws Exception {

        final String floorName = "";
        final String imagePath = "/4th";
        FloorRequestDTO request = createFloorRequestDTO(floorName, imagePath);


        this.mvc.perform(MockMvcRequestBuilders.post("/floors").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)).characterEncoding("utf-8")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorName)층 이름이 있어야 합니다.")));


    }

    private void createSeatWithInvalidImagePath() throws Exception {

        final String floorName = "4층";
        final String imagePath = "";
        FloorRequestDTO request = createFloorRequestDTO(floorName, imagePath);

        this.mvc.perform(MockMvcRequestBuilders.post("/floors").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)).characterEncoding("utf-8")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(imagePath)층 이미지 주소가 있어야 합니다.")));


    }

    @Test
    public void getFloorWitValidFloorId() throws Exception {
        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        FloorRequestDTO requestForCreate = createFloorRequestDTO(floorName, imagePath);
        Floor firstFloor = createFloorForTest(requestForCreate);
        Seat seat = createSeatForTest(firstFloor);

        long floorId = 1;
        Floor expectedEntity = findFloorById(floorId);
        given(floorService.getFloorById(ArgumentMatchers.eq(floorId))).willReturn(convertToResponseDTO(expectedEntity, FloorResponseDetailsDTO.class));


        this.mvc.perform(MockMvcRequestBuilders.get("/floors/" + floorId).cookie(jwt).characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.floorName", is(floorName)))
                .andExpect(jsonPath("$.imagePath", is(imagePath)))
                .andExpect(jsonPath("$.seats[*].coordinate.lng", contains(seat.getCoordinate().getLng().doubleValue())))
                .andExpect(jsonPath("$.seats[*].coordinate.lat", contains(seat.getCoordinate().getLat().doubleValue())));


    }

    @Test
    public void getSeatWithInvalidSeatId() throws Exception {


        long seatId = 0;

        this.mvc.perform(MockMvcRequestBuilders.get("/floors/" + seatId).cookie(jwt).characterEncoding("utf-8")
        )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(getFloorById.floorId)층 id 가 잘못되었습니다.")));
    }

    @Test
    public void updateFloorWithValidParams() throws Exception {

        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        FloorRequestDTO requestForCreate = createFloorRequestDTO(floorName, imagePath);
        createFloorForTest(requestForCreate);

        final String newFloorName = "5층";
        final String newImagePath = "/5thFloor";
        FloorRequestDTO requestForUpdate = createFloorRequestDTO(newFloorName, newImagePath);

        final long floorId = 1;
        Floor toBeUpdate = findFloorById(floorId);
        toBeUpdate.setFloorName(newFloorName);
        toBeUpdate.setImagePath(newImagePath);

        given(floorService.updateFloor(ArgumentMatchers.eq(floorId), ArgumentMatchers.any(FloorRequestDTO.class))).willReturn(convertToResponseDTO(toBeUpdate, FloorResponseDetailsDTO.class));

        this.mvc.perform(MockMvcRequestBuilders.patch("/floors/" + floorId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate)).characterEncoding("utf-8")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.floorName", is(newFloorName)))
                .andExpect(jsonPath("$.imagePath", is(newImagePath)));


    }

    @Test
    public void updateFloorWithInvalidParams() throws Exception {

        updateFloorWithInvalidRequestBody();
        updateFloorWithInvalidFloorId();


    }

    public void updateFloorWithInvalidRequestBody() throws Exception {
        final String floorName = "";
        final String imagePath = "";
        long floorId = 1;
        FloorRequestDTO requestForUpdate = createFloorRequestDTO(floorName, imagePath);
        this.mvc.perform(MockMvcRequestBuilders.patch("/floors/" + floorId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate)).characterEncoding("utf-8")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorName)층 이름이 있어야 합니다.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(imagePath)층 이미지 주소가 있어야 합니다.")));


    }

    public void updateFloorWithInvalidFloorId() throws Exception {
        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        long floorId = 0;
        FloorRequestDTO requestForUpdate = createFloorRequestDTO(floorName, imagePath);
        this.mvc.perform(MockMvcRequestBuilders.patch("/floors/" + floorId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate)).characterEncoding("utf-8")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(updateFloor.floorId)층 id 가 잘못되었습니다.")));


    }

    @Test
    public void deleteFloorWithValidFloorId() throws Exception {

        long floorId = 1;


        this.mvc.perform(MockMvcRequestBuilders.delete("/floors/" + floorId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    @Test
    public void deleteSeatWithInvalidFloorId() throws Exception {

        long floorId = 0;


        this.mvc.perform(MockMvcRequestBuilders.delete("/floors/" + floorId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.reason", containsString(
                        "(deleteFloor.floorId)층 id 가 잘못되었습니다.")));


    }


}
