package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.PlaceType;
import com.gabia.internproject.data.repository.FloorRepository;
import com.gabia.internproject.data.repository.PlaceTypeRepository;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.request.FloorRequestDTO;
import com.gabia.internproject.dto.request.PlaceRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.response.*;
import com.gabia.internproject.exception.customExceptions.FloorNotFoundException;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
//import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FloorServiceTest {

    @Autowired
    private FloorService floorService;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceTypeRepository placeTypeRepository;


    private PlaceType meetingRoomInDB;

    @Before
    public void a_init() throws Exception {
        placeTypeTableSettings();


    }

    @After
    public void removeAll() throws Exception {
        List<FloorResponseDTO> list = floorService.getAllFloors();
        for (FloorResponseDTO floor : list) {
            floorService.deleteFloor(floor.getId());
        }

    }

    public void placeTypeTableSettings() {
        PlaceType meetingRoom = new PlaceType();
        meetingRoom.setTypeName("meetingRoom");
        meetingRoomInDB = placeTypeRepository.save(meetingRoom);
    }


    public FloorRequestDTO createFloorRequestDTO(String floorName, String imagePath) {
        FloorRequestDTO request = new FloorRequestDTO();
        request.setFloorName(floorName);
        request.setImagePath(imagePath);
        return request;

    }

    public PlaceResponseDetailsDTO createPlaceForTest(long floorId) {

        PlaceType meetingRoom = new PlaceType();
        meetingRoom.setTypeName("meetingRoom");


        final long placeTypeId = meetingRoomInDB.getId();
        final String placeName = "meetingRoom_m";
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        CoordinateDTO cor = new CoordinateDTO();
        cor.setLat(lat);
        cor.setLng(lng);

        PlaceRequestDTO request = new PlaceRequestDTO();
        request.setPlaceTypeId(placeTypeId);
        request.setFloorId(floorId);
        request.setPlaceName(placeName);
        request.setCoordinate(cor);

        return placeService.createPlace(request);

    }


    public SeatResponseDetailsDTO createSeatForTest(long floorId) {
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        CoordinateDTO cor = new CoordinateDTO();
        cor.setLat(lat);
        cor.setLng(lng);
        SeatRequestDTO request = new SeatRequestDTO();
        request.setFloorId(floorId);
        request.setCoordinate(cor);
        return seatService.createSeat(request);
    }

    @Test
//    @Transactional
    public void getAllFloors() {
        final String floorName1 = "4층";
        final String imagePath1 = "/4thFloor";
        FloorRequestDTO request = createFloorRequestDTO(floorName1, imagePath1);
        FloorResponseDTO floor1 = floorService.createFloor(request);

        final String floorName2 = "5층";
        final String imagePath2 = "/5thFloor";
        request = createFloorRequestDTO(floorName2, imagePath2);
        FloorResponseDTO floor2 = floorService.createFloor(request);

        SeatResponseDTO seat1 = createSeatForTest(floor2.getId());
        SeatResponseDTO seat2 = createSeatForTest(floor2.getId());


        List<FloorResponseDTO> result = floorService.getAllFloors();

        Assert.assertThat(result.size(), is(2));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("floorName", Matchers.is(floorName1)),
                hasProperty("floorName", Matchers.is(floorName2))
        ));

        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("imagePath", Matchers.is(imagePath1)),
                hasProperty("imagePath", Matchers.is(imagePath2))
        ));


    }


    @Test
//    @Transactional
    public void createFloor() {

        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        FloorRequestDTO requestForCreate = createFloorRequestDTO(floorName, imagePath);

        FloorResponseDTO created1 = floorService.createFloor(requestForCreate);


        Assert.assertThat(created1.getFloorName(), is(floorName));
        Assert.assertThat(created1.getImagePath(), is(imagePath));


    }

    @Test
//    @Transactional
    public void getFloorById() {
        //층은 많지않으니 패스

        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        FloorRequestDTO requestForCreate = createFloorRequestDTO(floorName, imagePath);

        FloorResponseDTO createdFloor = floorService.createFloor(requestForCreate);

        FloorResponseDetailsDTO floorFound = floorService.getFloorById(createdFloor.getId());

        Assert.assertThat(floorFound.getFloorName(), is(floorName));
        Assert.assertThat(floorFound.getImagePath(), is(imagePath));

    }

    @Test
//    @Transactional
    public void updateFloor() {

        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        FloorRequestDTO requestForCreate = createFloorRequestDTO(floorName, imagePath);

        FloorResponseDTO floor = floorService.createFloor(requestForCreate);

        final String newFloorName = "5층";
        final String newImagePath = "/5thFloor";
        FloorRequestDTO requestForUpdate = createFloorRequestDTO(newFloorName, newImagePath);

        final long floorId = floor.getId();

        FloorResponseDetailsDTO updatedFloor = floorService.updateFloor(floorId, requestForUpdate);

        Assert.assertThat(updatedFloor.getFloorName(), is(newFloorName));
        Assert.assertThat(updatedFloor.getImagePath(), is(newImagePath));

    }


    @Test(expected = FloorNotFoundException.class)
    // @Transactional
    public void deleteSeat() {


        final String floorName = "4층";
        final String imagePath = "/4thFloor";
        FloorRequestDTO requestForCreate = createFloorRequestDTO(floorName, imagePath);

        FloorResponseDTO createdFloor = floorService.createFloor(requestForCreate);


        createSeatForTest(createdFloor.getId());
        createSeatForTest(createdFloor.getId());

        createPlaceForTest(createdFloor.getId());
        createPlaceForTest(createdFloor.getId());

        List<SeatResponseDetailsDTO> seatList = seatService.getAllSeats();
        List<PlaceResponseDetailsDTO> placeList = placeService.getAllPlaces();

        Assert.assertThat(seatList.size(), is(2));
        Assert.assertThat(placeList.size(), is(2));

        floorService.deleteFloor(createdFloor.getId());


        seatList = seatService.getAllSeats();
        placeList = placeService.getAllPlaces();

        Assert.assertThat(seatList.size(), is(0));
        Assert.assertThat(placeList.size(), is(0));
        floorService.getFloorById(createdFloor.getId());

    }


}
