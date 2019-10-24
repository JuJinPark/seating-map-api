package com.gabia.internproject.service;


import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.data.entity.PlaceType;
import com.gabia.internproject.data.repository.FloorRepository;
import com.gabia.internproject.data.repository.PlaceRepository;
import com.gabia.internproject.data.repository.PlaceTypeRepository;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.request.PlaceRequestDTO;
import com.gabia.internproject.dto.response.FloorResponseDetailsDTO;
import com.gabia.internproject.dto.response.PlaceResponseDetailsDTO;
import com.gabia.internproject.exception.customExceptions.PlaceNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

//import org.h2.tools.Server;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlaceServiceTest {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    FloorService floorService;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    PlaceTypeRepository placeTypeRepository;


    private List<Long> floorIdList = new ArrayList<>();

    private Map<Long, Floor> floorList = new HashMap();

    private List<Long> placeTypeIdList = new ArrayList<>();

    private Map<Long, PlaceType> placeTypeList = new HashMap();


    @Before
    public void a_init() throws Exception {

        floorTableSettings();
        placeTypeTableSettings();

    }

    @After
    public void removeAll() throws Exception {
        placeRepository.deleteAll();
    }


    public void floorTableSettings() {
        Floor first = Floor.createNewFloor("4층", "/4thFloor");
        Floor firstInDB = floorRepository.save(first);
        floorIdList.add(firstInDB.getId());
        floorList.put(firstInDB.getId(), first);

        Floor second = Floor.createNewFloor("5층", "/5thFloor");
        Floor secondInDB = floorRepository.save(second);
        floorIdList.add(secondInDB.getId());
        floorList.put(secondInDB.getId(), second);
    }

    public void placeTypeTableSettings() {

        PlaceType meetingRoom = new PlaceType();
        meetingRoom.setTypeName("meetingRoom");
        PlaceType meetingRoomInDB = placeTypeRepository.save(meetingRoom);
        placeTypeIdList.add(meetingRoomInDB.getId());
        placeTypeList.put(meetingRoomInDB.getId(), meetingRoom);

        PlaceType etc = new PlaceType();
        etc.setTypeName("etc");
        PlaceType etcRoomInDB = placeTypeRepository.save(etc);
        placeTypeIdList.add(etcRoomInDB.getId());
        placeTypeList.put(etcRoomInDB.getId(), etc);

    }


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

    private void printStartLine() {

        System.out.println("---------------------------start-----------------------------------------");

    }

    private void printEndLine() {

        System.out.println("---------------------------end-----------------------------------------");

    }

    @Test
    public void createPlace() {

        final long floorId = floorIdList.get(0);
        final long placeTypeId = placeTypeIdList.get(0);
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(floorId, placeTypeId, placeName, lat, lng);
        PlaceResponseDetailsDTO created1 = placeService.createPlace(request);


        Assert.assertThat(created1.getFloor().getFloorName(), is(floorList.get(floorId).getFloorName()));
        Assert.assertThat(created1.getPlaceType().getTypeName(), is(placeTypeList.get(placeTypeId).getTypeName()));
        Assert.assertThat(created1.getPlaceName(), is(placeName));
        Assert.assertThat(created1.getCoordinate().getLat().doubleValue(), is(lat.doubleValue()));
        Assert.assertThat(created1.getCoordinate().getLng().doubleValue(), is(lng.doubleValue()));

        FloorResponseDetailsDTO firstFloor = floorService.getFloorById(created1.getFloor().getId());
        Assert.assertThat(firstFloor.getPlaces().get(0).getId(), is(created1.getId()));

    }


    @Test
//    @Transactional
    public void getAllPlaces() {
        final long floorId = floorIdList.get(0);
        final long placeTypeId = placeTypeIdList.get(0);
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(floorId, placeTypeId, placeName, lat, lng);
        placeService.createPlace(request);

        final long floorId2 = floorIdList.get(0);
        final long placeTypeId2 = placeTypeIdList.get(1);
        final String placeName2 = "meetingRoom_s";
        final BigDecimal lat2 = new BigDecimal("12.852");
        final BigDecimal lng2 = new BigDecimal("-1111.44");

        request = createPlaceRequestDTO(floorId2, placeTypeId2, placeName2, lat2, lng2);
        placeService.createPlace(request);

        List<PlaceResponseDetailsDTO> result = placeService.getAllPlaces();

        Assert.assertThat(result.size(), is(2));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("placeName", is("meetingRoom_m")),
                hasProperty("placeName", is("meetingRoom_s"))
        ));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("floor", hasProperty("floorName", is(floorList.get(floorId).getFloorName()))),
                hasProperty("floor", hasProperty("floorName", is(floorList.get(floorId2).getFloorName())))
        ));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("placeType", hasProperty("typeName", is(placeTypeList.get(placeTypeId).getTypeName()))),
                hasProperty("placeType", hasProperty("typeName", is(placeTypeList.get(placeTypeId2).getTypeName())))
        ));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("coordinate", hasProperty("lat", is(closeTo(lat, new BigDecimal(0.000001))))),
                hasProperty("coordinate", hasProperty("lat", is(closeTo(lat2, new BigDecimal(0.000001)))))
        ));

        FloorResponseDetailsDTO floor = floorService.getFloorById(floorId);
//        List<Place> placeList=floorRepository.findById(floorId).orElse(null).getPlaces();
        Assert.assertThat(floor.getPlaces().size(), is(2));

    }


    @Test
//    @Transactional
    public void getPlaceById() {

        final long floorId = floorIdList.get(0);
        final long placeTypeId = placeTypeIdList.get(0);
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(floorId, placeTypeId, placeName, lat, lng);
        PlaceResponseDetailsDTO created = placeService.createPlace(request);
        placeService.createPlace(request);

        printStartLine();
        PlaceResponseDetailsDTO place = placeService.getPlaceById(created.getId());
        printEndLine();

        Assert.assertThat(place.getFloor().getFloorName(), is(floorList.get(floorId).getFloorName()));
        Assert.assertThat(place.getPlaceType().getTypeName(), is(placeTypeList.get(placeTypeId).getTypeName()));
        Assert.assertThat(place.getPlaceName(), is(placeName));
        Assert.assertThat(place.getCoordinate().getLat().doubleValue(), is(lat.doubleValue()));
        Assert.assertThat(place.getCoordinate().getLng().doubleValue(), is(lng.doubleValue()));

    }

    @Test
    // @Transactional
    public void updatePlace() {

        final long floorId = floorIdList.get(0);
        final long placeTypeId = placeTypeIdList.get(0);
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(floorId, placeTypeId, placeName, lat, lng);
        PlaceResponseDetailsDTO created = placeService.createPlace(request);

        final long newFloorId = floorIdList.get(1);
        final long newPlaceTypeId = placeTypeIdList.get(0);
        final String newPlaceName = "meetingRoom_etc";
        final BigDecimal newLat = new BigDecimal("11.225");
        final BigDecimal newLng = new BigDecimal("-1.2544");
        PlaceRequestDTO updateRequest = createPlaceRequestDTO(newFloorId, newPlaceTypeId, newPlaceName, newLat, newLng);

        printStartLine();
        PlaceResponseDetailsDTO updatedPlace = placeService.updatePlace(created.getId(), updateRequest);
        printEndLine();


        FloorResponseDetailsDTO oldFloor = floorService.getFloorById(floorId);
        FloorResponseDetailsDTO newFloor = floorService.getFloorById(newFloorId);

//        Floor oldFloor=floorRepository.findById(floorId).orElse(null);
//        Floor newFloor=floorRepository.findById(newFloorId).orElse(null);

        Assert.assertThat(updatedPlace.getFloor().getFloorName(), is(floorList.get(newFloorId).getFloorName()));
        Assert.assertThat(updatedPlace.getPlaceType().getTypeName(), is(placeTypeList.get(newPlaceTypeId).getTypeName()));
        Assert.assertThat(updatedPlace.getPlaceName(), is(newPlaceName));
        Assert.assertThat(updatedPlace.getCoordinate().getLat().doubleValue(), is(newLat.doubleValue()));
        Assert.assertThat(updatedPlace.getCoordinate().getLng().doubleValue(), is(newLng.doubleValue()));

        Assert.assertThat(oldFloor.getPlaces().size(), is(0));
        Assert.assertThat(newFloor.getPlaces().get(0).getId(), is(updatedPlace.getId()));
    }


    @Test(expected = PlaceNotFoundException.class)
    //   @Transactional
    public void deletePlace() {
// 성능 향상 필요
        final long floorId = floorIdList.get(0);
        final long placeTypeId = placeTypeIdList.get(0);
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(floorId, placeTypeId, placeName, lat, lng);
        PlaceResponseDetailsDTO created = placeService.createPlace(request);


        printStartLine();
        placeService.deletePlace(created.getId());
        printEndLine();
        FloorResponseDetailsDTO floor = floorService.getFloorById(floorId);
        Assert.assertThat(floor.getPlaces().size(), is(0));

        placeService.getPlaceById(created.getId());
    }

    @Test
//    @Transactional
    public void deleteAllByFloorId() {
        final long floorId = floorIdList.get(0);
        final long placeTypeId = placeTypeIdList.get(0);
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");

        PlaceRequestDTO request = createPlaceRequestDTO(floorId, placeTypeId, placeName, lat, lng);
        placeService.createPlace(request);


        final long placeTypeId2 = placeTypeIdList.get(1);
        final String placeName2 = "meetingRoom_s";
        final BigDecimal lat2 = new BigDecimal("12.852");
        final BigDecimal lng2 = new BigDecimal("-1111.44");

        request = createPlaceRequestDTO(floorId, placeTypeId2, placeName2, lat2, lng2);
        placeService.createPlace(request);

        placeService.deleteAllByFloorId(floorId);

        List<PlaceResponseDetailsDTO> result = placeService.getAllPlaces();

        Assert.assertThat(result.size(), is(0));

    }


}

