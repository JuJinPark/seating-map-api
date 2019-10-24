package com.gabia.internproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.config.TestConfig;
import com.gabia.internproject.data.entity.*;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.request.FloorRequestDTO;
import com.gabia.internproject.dto.request.PlaceRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.response.*;
import com.gabia.internproject.service.FloorService;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.OAuthConstants;
import com.gabia.internproject.service.PlaceService;
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

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(PlaceController.class)
@Import(TestConfig.class)
public class PlaceControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PlaceService placeService;

    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static ObjectMappingUtil mapper;

    private List<Place> placeTable = new ArrayList<>();
    private static List<Floor> floorTable = new ArrayList<>();
    private static List<PlaceType> placeTypeTable = new ArrayList<>();

    private Cookie jwt;

    @BeforeClass
    public static void init() throws Exception {

        mapper = new MapStructUtil();

        placeTypeTableSettings();

        floorTableSettings();

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


    public static void placeTypeTableSettings() {
        PlaceType meetingRoom = new PlaceType();
        meetingRoom.setTypeName("meetingRoom");
        PlaceType etc = new PlaceType();
        etc.setTypeName("etc");
        placeTypeTable.add(meetingRoom);
        placeTypeTable.add(etc);

    }

    public static void floorTableSettings() {
        Floor first = Floor.createNewFloor("4층", "/4thFloor");
        Floor second = Floor.createNewFloor("5층", "/5thFloor");

        floorTable.add(first);
        floorTable.add(second);
    }

//    private Place createPlaceForTest(long floorId,long placeTypeId,String placeName, BigDecimal lat, BigDecimal lng){
//        PlaceRequestDTO request=createPlaceRequestDTO(floorId,placeTypeId,placeName,lat,lng);
//        return createPlace(request);
//    }

    private PlaceRequestDTO createPlaceRequestDTO(long floorId, long placeTypeId, String placeName, BigDecimal lat, BigDecimal lng) {

        PlaceRequestDTO request = new PlaceRequestDTO();
        request.setFloorId(floorId);
        request.setPlaceTypeId(placeTypeId);
        request.setPlaceName(placeName);
        CoordinateDTO cor = new CoordinateDTO();
        cor.setLat(lat);
        cor.setLng(lng);
        request.setCoordinate(cor);


        return request;
    }


    private Place createPlaceForTest(PlaceRequestDTO placeRequestDTO) {
        Floor floor = findFloorById(placeRequestDTO.getFloorId());
        PlaceType placeType = findPlaceTypeById(placeRequestDTO.getPlaceTypeId());
        Place place = Place.CreateNewPlace(floor, placeType, placeRequestDTO.getPlaceName(), new Coordinate(placeRequestDTO.getCoordinate().getLat(), placeRequestDTO.getCoordinate().getLng()));
        placeTable.add(place);
        return place;
    }

    private Floor findFloorById(long floorId) {

        return floorTable.get((int) floorId - 1);
    }

    private Place findPlaceById(long placeId) {
        return placeTable.get((int) placeId - 1);
    }

    private PlaceType findPlaceTypeById(long placeTypeId) {

        return placeTypeTable.get((int) placeTypeId - 1);
    }

    public String convertToJson(PlaceRequestDTO request) throws JsonProcessingException {

        return jsonMapper.writeValueAsString(request);
    }

    public PlaceResponseDetailsDTO convertToResponseDTO(Place place) {
        PlaceResponseDetailsDTO ss=mapper.convertToDTO(place, PlaceResponseDetailsDTO.class);
        return ss;
    }

    public List<PlaceResponseDetailsDTO> convertToResponseDTO(List<Place> place) {
        return mapper.convertToDTO(place, PlaceResponseDetailsDTO.class);
    }

    @Test
    public void createPlace() throws Exception {

        final long firstFloorId = 1;
        final long placeTypeId = 1;
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(firstFloorId, placeTypeId, placeName, lat, lng);
        Place place = createPlaceForTest(request);

        given(placeService.createPlace(ArgumentMatchers.any(PlaceRequestDTO.class))).willReturn(convertToResponseDTO(place));


        this.mvc.perform(MockMvcRequestBuilders.post("/places").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
                .characterEncoding("utf-8")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.placeName", is(placeName)))
                .andExpect(jsonPath("$.floor.floorName", is(findFloorById(firstFloorId).getFloorName())))
                .andExpect(jsonPath("$.placeType.typeName", is(findPlaceTypeById(placeTypeId).getTypeName())))
                .andExpect(jsonPath("$.coordinate.lat", is(lat.doubleValue())))
                .andExpect(jsonPath("$.coordinate.lng", is(lng.doubleValue())));


    }

    @Test
    public void createPlaceWithInvalidParams() throws Exception {

        createPlaceWithAllInvalidParams();

        createPlaceWithInvalidPlaceTypeId();

        createPlaceWithInvalidPlaceName();

    }

    private void createPlaceWithAllInvalidParams() throws Exception {
        PlaceRequestDTO request = new PlaceRequestDTO();

        this.mvc.perform(MockMvcRequestBuilders.post("/places").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
                .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorId)층 아이디를 입력하세요.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(placeTypeId)장소타입 아이디를 입력하세요.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(placeName)장소명이 있어야 합니다.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(coordinate)좌표값이 있어야 합니다.")));


    }

    private void createPlaceWithInvalidPlaceTypeId() throws Exception {
        final long firstFloorId = 1;
        final long placeTypeId = 0;
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(firstFloorId, placeTypeId, placeName, lat, lng);

        this.mvc.perform(MockMvcRequestBuilders.post("/places").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
                .characterEncoding("utf-8")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(placeTypeId)장소타입 아이디를 입력하세요.")));


    }

    private void createPlaceWithInvalidPlaceName() throws Exception {

        final long firstFloorId = 1;
        final long placeTypeId = 1;
        final String placeName = "  ";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(firstFloorId, placeTypeId, placeName, lat, lng);

        this.mvc.perform(MockMvcRequestBuilders.post("/places").cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
                .characterEncoding("utf-8")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(placeName)장소명이 있어야 합니다.")));


    }


    @Test
    public void getAllPlaces() throws Exception {
        final long firstFloorId = 1;
        final long placeTypeId = 1;
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(firstFloorId, placeTypeId, placeName, lat, lng);
        Place meetingRoomM = createPlaceForTest(request);

        final long secondFloorId = 2;
        final long placeTypeId2 = 1;
        final String placeName2 = "meetingRoom_s";
        final BigDecimal lat2 = new BigDecimal("12.123");
        final BigDecimal lng2 = new BigDecimal("11.44");
        request = createPlaceRequestDTO(secondFloorId, placeTypeId2, placeName2, lat2, lng2);
        Place meetingRoomS = createPlaceForTest(request);

        given(placeService.getAllPlaces()).willReturn(convertToResponseDTO(placeTable));

        this.mvc.perform(MockMvcRequestBuilders.get("/places").cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].placeName", containsInAnyOrder(placeName, placeName2)))
                .andExpect(jsonPath("$[*].coordinate.lat", containsInAnyOrder(lat.doubleValue(), lat2.doubleValue())))
                .andExpect(jsonPath("$[*].floor.floorName", containsInAnyOrder(findFloorById(firstFloorId).getFloorName(), findFloorById(secondFloorId).getFloorName())))
                .andExpect(jsonPath("$[*].placeType.typeName", containsInAnyOrder(findPlaceTypeById(placeTypeId).getTypeName(), findPlaceTypeById(placeTypeId2).getTypeName())));
    }

    @Test
    public void getPlaceById() throws Exception {
        final long firstFloorId = 1;
        final long placeTypeId = 1;
        final String placeName = "meetingRoomM";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        PlaceRequestDTO request = createPlaceRequestDTO(firstFloorId, placeTypeId, placeName, lat, lng);
        createPlaceForTest(request);

        long placeId = 1;
        Place expectedEntity = findPlaceById(placeId);
        given(placeService.getPlaceById(ArgumentMatchers.eq(placeId))).willReturn(convertToResponseDTO(expectedEntity));


        this.mvc.perform(MockMvcRequestBuilders.get("/places/" + placeId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.placeName", is(placeName)))
                .andExpect(jsonPath("$.floor.floorName", is(findFloorById(firstFloorId).getFloorName())))
                .andExpect(jsonPath("$.placeType.typeName", is(findPlaceTypeById(placeTypeId).getTypeName())))
                .andExpect(jsonPath("$.coordinate.lat", is(lat.doubleValue())))
                .andExpect(jsonPath("$.coordinate.lng", is(lng.doubleValue())));


    }

    @Test
    public void getPlaceByInvalidPlaceId() throws Exception {

        long placeId = 0;

        this.mvc.perform(MockMvcRequestBuilders.get("/places/" + placeId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(getPlaceById.placeId)장소 id 가 잘못되었습니다.")));
    }

    @Test
    public void updatePlace() throws Exception {

        final long firstFloorId = 1;
        final long placeTypeId = 1;
        final String placeName = "meetingRoomM";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        PlaceRequestDTO request = createPlaceRequestDTO(firstFloorId, placeTypeId, placeName, lat, lng);
        createPlaceForTest(request);

        final long newFloorId = 2;
        final long newPlaceTypeId = 2;
        final String newPlaceName = "meetingRoomS";
        final BigDecimal newLat = new BigDecimal("12.1");
        final BigDecimal newLng = new BigDecimal("1.44");
        PlaceRequestDTO requestForUpdate = createPlaceRequestDTO(newFloorId, newPlaceTypeId, newPlaceName, newLat, newLng);

        final long placeId = 1;
        Place toBeUpdate = findPlaceById(placeId);
        toBeUpdate.setFloor(findFloorById(newFloorId));
        toBeUpdate.setPlaceType(findPlaceTypeById(newPlaceTypeId));
        toBeUpdate.setPlaceName(newPlaceName);
        toBeUpdate.getCoordinate().setLat(newLat);
        toBeUpdate.getCoordinate().setLng(newLng);

        given(placeService.updatePlace(ArgumentMatchers.eq(placeId),ArgumentMatchers.any(PlaceRequestDTO.class))).willReturn(convertToResponseDTO(toBeUpdate));

        this.mvc.perform(MockMvcRequestBuilders.patch("/places/" + placeId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.placeName", is(newPlaceName)))
                .andExpect(jsonPath("$.floor.floorName", is(findFloorById(newFloorId).getFloorName())))
                .andExpect(jsonPath("$.placeType.typeName", is(findPlaceTypeById(newPlaceTypeId).getTypeName())))
                .andExpect(jsonPath("$.coordinate.lat", is(newLat.doubleValue())))
                .andExpect(jsonPath("$.coordinate.lng", is(newLng.doubleValue())));


    }

    @Test
    public void updatePlaceWithInvalidParams() throws Exception {

        updatePlaceWithInvalidRequestBody();
        updatePlaceWithInvalidPlaceId();


    }

    private void updatePlaceWithInvalidRequestBody() throws Exception {
        final long placeId = 1;

        PlaceRequestDTO requestForUpdate = new PlaceRequestDTO();

        this.mvc.perform(MockMvcRequestBuilders.patch("/places/" + placeId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
                .characterEncoding("utf-8")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(floorId)층 아이디를 입력하세요.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(placeTypeId)장소타입 아이디를 입력하세요.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(placeName)장소명이 있어야 합니다.")))
                .andExpect(jsonPath("$.reason", containsString(
                        "(coordinate)좌표값이 있어야 합니다.")));

    }

    private void updatePlaceWithInvalidPlaceId() throws Exception {

        final long placeId = 0;

        final long firstFloorId = 1;
        final long placeTypeId = 1;
        final String placeName = "meetingRoomM";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO requestForUpdate = createPlaceRequestDTO(firstFloorId, placeTypeId, placeName, lat, lng);

        this.mvc.perform(MockMvcRequestBuilders.patch("/places/" + placeId).cookie(jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(requestForUpdate))
                .characterEncoding("utf-8")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.reason", containsString(
                        "(updatePlace.placeId)장소 id 가 잘못되었습니다.")));

    }


    @Test
    public void deletePlace() throws Exception {

        long placeId = 1;

        this.mvc.perform(MockMvcRequestBuilders.delete("/places/" + placeId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    @Test
    public void deleteSeatWithInvalidFloorId() throws Exception {

        long placeId = 0;


        this.mvc.perform(MockMvcRequestBuilders.delete("/places/" + placeId).cookie(jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.reason", containsString(
                        "(deletePlace.placeId)장소 id 가 잘못되었습니다.")));


    }


}
