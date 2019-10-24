package com.gabia.internproject.service;


import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.data.entity.Seat;
import com.gabia.internproject.data.repository.EmployeeRepository;
import com.gabia.internproject.data.repository.FloorRepository;

import com.gabia.internproject.data.repository.SeatRepository;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.request.SeatEmployeeUpdateRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.request.SeatUpdateRequestDTO;
import com.gabia.internproject.dto.response.FloorResponseDetailsDTO;
import com.gabia.internproject.dto.response.SeatResponseDTO;
import com.gabia.internproject.dto.response.SeatResponseDetailsDTO;
import com.gabia.internproject.exception.customExceptions.SeatNotFoundException;


import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SeatServiceTest {

    @Autowired
    private SeatService seatService;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    FloorService floorService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    private static boolean initialized = false;

    private List<Long> floorIdList = new ArrayList<>();

    private Map<Long, Floor> floorList = new HashMap();

    private List<Long> employeeIdList = new ArrayList<>();

    private Map<Long, Employee> employeeList = new HashMap();


    @Before
    public void a_init() throws Exception {

        floorTableSettings();
        employeeTableSettings();


    }

    @After
    public void removeAll() throws Exception {
        seatRepository.deleteAll();
    }

    private void printStartLine() {

        System.out.println("---------------------------start-----------------------------------------");

    }

    private void printEndLine() {

        System.out.println("---------------------------end-----------------------------------------");

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

    public void employeeTableSettings() {
        //     Employee employee1= Employee.CreateNewEmployee("익명1","gabialeader.com","mm123");
        Employee employee1 = new Employee();
        employee1.setEmployeeName("익명1");
        employee1.setCompanyEmail("gabialeader.com");
        employee1.setEmployeeNumber("mm123");

        Employee firstInDB = employeeRepository.save(employee1);
        employeeIdList.add(firstInDB.getId());
        employeeList.put(firstInDB.getId(), employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeName("익명2");
        employee2.setCompanyEmail("gabiamember.com");
        employee2.setEmployeeNumber("mm1234");
//        Employee employee2=Employee.CreateNewEmployee("익명2","gabiamember.com","mm1234");
        Employee secondInDB = employeeRepository.save(employee2);
        employeeIdList.add(secondInDB.getId());
        employeeList.put(secondInDB.getId(), employee2);

    }

    public SeatEmployeeUpdateRequestDTO createSeatEmployeeUpdateRequestDTO(long employeeId) {

        SeatEmployeeUpdateRequestDTO request = new SeatEmployeeUpdateRequestDTO();
        request.setEmployeeId(employeeId);

        return request;
    }

    public SeatRequestDTO createSeatRequestDTO(long floorId, BigDecimal lat, BigDecimal lng) {
        CoordinateDTO cor = new CoordinateDTO();
        cor.setLat(lat);
        cor.setLng(lng);

        SeatRequestDTO request = new SeatRequestDTO();
        request.setFloorId(floorId);
        request.setCoordinate(cor);

        return request;
    }

    public SeatUpdateRequestDTO createSeatUpdateRequestDTO(long seatId, long floorId, BigDecimal lat, BigDecimal lng) {
        CoordinateDTO cor = new CoordinateDTO();
        cor.setLat(lat);
        cor.setLng(lng);

        SeatUpdateRequestDTO request = new SeatUpdateRequestDTO();
        request.setId(seatId);
        request.setFloorId(floorId);
        request.setCoordinate(cor);
        return request;

    }

    @Test
//    @Transactional
    public void createSeat() {

        final long firstFloorId = floorIdList.get(0);
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);

        printStartLine();
        SeatResponseDetailsDTO created1 = seatService.createSeat(requestForCreate);
        printEndLine();


        Assert.assertThat(created1.getCoordinate().getLat().doubleValue(), is(firstLat.doubleValue()));
        Assert.assertThat(created1.getCoordinate().getLng().doubleValue(), is(firstLng.doubleValue()));
        Assert.assertThat(created1.getFloor().getFloorName(), is(floorList.get(firstFloorId).getFloorName()));
        Assert.assertThat(created1.getFloor().getImagePath(), is(floorList.get(firstFloorId).getImagePath()));

        FloorResponseDetailsDTO firstFloor = floorService.getFloorById(firstFloorId);
        Assert.assertThat(firstFloor.getSeats().get(0).getId(), is(created1.getId()));

    }

    @Test
    //  @Transactional
    public void updateSeat() {

        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);
        SeatResponseDetailsDTO createdSeat = seatService.createSeat(requestForCreate);

        final long newFloorId = floorIdList.get(1);
        final BigDecimal newLat = new BigDecimal("11.225");
        final BigDecimal newLng = new BigDecimal("-1.2544");
        SeatRequestDTO updateRequest = createSeatRequestDTO(newFloorId, newLat, newLng);

        printStartLine();
        SeatResponseDetailsDTO updatedSeat = seatService.updateSeat(createdSeat.getId(), updateRequest);
        printEndLine();


        FloorResponseDetailsDTO firstFloor = floorService.getFloorById(firstFloorId);
        FloorResponseDetailsDTO secondFloor = floorService.getFloorById(newFloorId);

        Assert.assertThat(updatedSeat.getCoordinate().getLat().doubleValue(), is(newLat.doubleValue()));
        Assert.assertThat(updatedSeat.getCoordinate().getLng().doubleValue(), is(newLng.doubleValue()));
        Assert.assertThat(updatedSeat.getFloor().getFloorName(), is(floorList.get(newFloorId).getFloorName()));
        Assert.assertThat(updatedSeat.getFloor().getImagePath(), is(floorList.get(newFloorId).getImagePath()));
        Assert.assertThat(secondFloor.getSeats().get(0).getCoordinate().getLng().doubleValue(), is(newLng.doubleValue()));
        Assert.assertThat(firstFloor.getSeats().size(), is(0));


    }

    @Test
    //@Transactional
    public void updateSeatWithNoOwnerWithEmployeeWithNoSeat() {

        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);
        SeatResponseDetailsDTO createdSeat = seatService.createSeat(requestForCreate);

        final long employeeIdToUpdate = employeeIdList.get(1);
        SeatEmployeeUpdateRequestDTO updateRequest = createSeatEmployeeUpdateRequestDTO(employeeIdToUpdate);

        printStartLine();
        SeatResponseDetailsDTO updatedSeat = seatService.updateSeatWithEmployee(createdSeat.getId(), updateRequest);
        printEndLine();

        Assert.assertThat(updatedSeat.getEmployee().getEmployeeName(), is(employeeList.get(employeeIdToUpdate).getEmployeeName()));
        Assert.assertThat(updatedSeat.getEmployee().getCompanyEmail(), is(employeeList.get(employeeIdToUpdate).getCompanyEmail()));
        Assert.assertThat(updatedSeat.getEmployee().getEmployeeNumber(), is(employeeList.get(employeeIdToUpdate).getEmployeeNumber()));

        final Employee ownerOfSeat = employeeRepository.findById(employeeIdToUpdate).orElse(null);
        Assert.assertThat(ownerOfSeat.getSeat().getId(), is(updatedSeat.getId()));
        Assert.assertThat(ownerOfSeat.getSeat().getCoordinate().getLat().doubleValue(), is(lat.doubleValue()));


    }

    @Test
    //@Transactional
    public void updateSeatWithOwnerWithEmployeeWithNoSeat() {

        //given
        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);
        SeatResponseDetailsDTO createdSeat = seatService.createSeat(requestForCreate);

        final long previousOwnerId = employeeIdList.get(1);
        SeatEmployeeUpdateRequestDTO updateRequest = createSeatEmployeeUpdateRequestDTO(previousOwnerId);

        SeatResponseDetailsDTO updatedSeat = seatService.updateSeatWithEmployee(createdSeat.getId(), updateRequest);

        //when 자리가 없는 사람을 사람이있는 자리에 배정
        final long newEmployeeIdToUpdate = employeeIdList.get(0);
        updateRequest = createSeatEmployeeUpdateRequestDTO(newEmployeeIdToUpdate);

        printStartLine();
        updatedSeat = seatService.updateSeatWithEmployee(createdSeat.getId(), updateRequest);
        printEndLine();

        //then 새자리에 직원정보 검사, 직원에 새자리 검사, 그전 직원의 자리 검사
        Assert.assertThat(updatedSeat.getEmployee().getEmployeeName(), is(employeeList.get(newEmployeeIdToUpdate).getEmployeeName()));
        Assert.assertThat(updatedSeat.getEmployee().getCompanyEmail(), is(employeeList.get(newEmployeeIdToUpdate).getCompanyEmail()));
        Assert.assertThat(updatedSeat.getEmployee().getEmployeeNumber(), is(employeeList.get(newEmployeeIdToUpdate).getEmployeeNumber()));

        Employee newOwner = employeeService.getEmployeeEntityById(newEmployeeIdToUpdate);
        Assert.assertThat(newOwner.getSeat().getId(), is(updatedSeat.getId()));
        Assert.assertThat(newOwner.getSeat().getCoordinate().getLat().doubleValue(), is(lat.doubleValue()));

        Employee previousOwner = employeeService.getEmployeeEntityById(previousOwnerId);
        Assert.assertThat(previousOwner.getSeat(), is(nullValue()));


    }

    @Test
    //@Transactional
    public void updateSeatWithNoOwnerWithEmployeeWithSeat() {

        //given
        //그전 자리
        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);
        SeatResponseDetailsDTO createdSeat = seatService.createSeat(requestForCreate);
        // 직원 그전 자리에 배정
        final long ownerId = employeeIdList.get(1);
        SeatEmployeeUpdateRequestDTO updateRequest = createSeatEmployeeUpdateRequestDTO(ownerId);
        SeatResponseDetailsDTO updatedSeat = seatService.updateSeatWithEmployee(createdSeat.getId(), updateRequest);

        // 새자리
        final BigDecimal lat2 = new BigDecimal("12.8512");
        final BigDecimal lng2 = new BigDecimal("-1111.4554");
        requestForCreate = createSeatRequestDTO(firstFloorId, lat2, lng2);
        SeatResponseDetailsDTO createdEmptySeat = seatService.createSeat(requestForCreate);


        //when 자리가 있는 사람을 비어있는 자리에 배정
        updateRequest = createSeatEmployeeUpdateRequestDTO(ownerId);

        printStartLine();
        updatedSeat = seatService.updateSeatWithEmployee(createdEmptySeat.getId(), updateRequest);
        printEndLine();

        //then 새자리에 직원정보 검사
        Assert.assertThat(updatedSeat.getEmployee().getEmployeeName(), is(employeeList.get(ownerId).getEmployeeName()));
        Assert.assertThat(updatedSeat.getEmployee().getCompanyEmail(), is(employeeList.get(ownerId).getCompanyEmail()));
        Assert.assertThat(updatedSeat.getEmployee().getEmployeeNumber(), is(employeeList.get(ownerId).getEmployeeNumber()));

        //then 직원에 새자리 검사
        Employee newOwner = employeeService.getEmployeeEntityById(ownerId);
        Assert.assertThat(newOwner.getSeat().getId(), is(updatedSeat.getId()));
        Assert.assertThat(newOwner.getSeat().getCoordinate().getLat().doubleValue(), is(lat2.doubleValue()));

        //then 그전 자리 검사
        SeatResponseDetailsDTO previousSeat = seatService.getSeatById(createdSeat.getId());
        Assert.assertThat(previousSeat.getEmployee(), is(nullValue()));


    }

    @Test
    //@Transactional
    public void updateSeatWithOwnerWithEmployeeWithSeat() {

        //given
        //첫번쨰 자리
        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);
        SeatResponseDetailsDTO createdSeat1 = seatService.createSeat(requestForCreate);
        // 직원2 첫번쨰 자리에 배정
        long owner2Id = employeeIdList.get(1);
        SeatEmployeeUpdateRequestDTO updateRequest = createSeatEmployeeUpdateRequestDTO(owner2Id);
        SeatResponseDetailsDTO updatedSeat = seatService.updateSeatWithEmployee(createdSeat1.getId(), updateRequest);

        // 두번쨰 자리
        final BigDecimal lat2 = new BigDecimal("12.8512");
        final BigDecimal lng2 = new BigDecimal("-1111.4554");
        requestForCreate = createSeatRequestDTO(firstFloorId, lat2, lng2);
        SeatResponseDetailsDTO createdSeat2 = seatService.createSeat(requestForCreate);
        // 직원1 두번쨰 자리에 배정
        long owner1Id = employeeIdList.get(0);
        updateRequest = createSeatEmployeeUpdateRequestDTO(owner1Id);
        updatedSeat = seatService.updateSeatWithEmployee(createdSeat2.getId(), updateRequest);


        //when 첫번쨰 자리에 직원1 배정
        updateRequest = createSeatEmployeeUpdateRequestDTO(owner1Id);
        printStartLine();
        updatedSeat = seatService.updateSeatWithEmployee(createdSeat1.getId(), updateRequest);
        printEndLine();

        //then 첫번쨰 자리에 직원1 정보 검사
        Assert.assertThat(updatedSeat.getEmployee().getEmployeeName(), is(employeeList.get(owner1Id).getEmployeeName()));
        Assert.assertThat(updatedSeat.getEmployee().getCompanyEmail(), is(employeeList.get(owner1Id).getCompanyEmail()));
        Assert.assertThat(updatedSeat.getEmployee().getEmployeeNumber(), is(employeeList.get(owner1Id).getEmployeeNumber()));

        //then 직원1에 자리 정보 검사
        Employee newOwner = employeeService.getEmployeeEntityById(owner1Id);
        Assert.assertThat(newOwner.getSeat().getId(), is(updatedSeat.getId()));
        Assert.assertThat(newOwner.getSeat().getCoordinate().getLat().doubleValue(), is(lat.doubleValue()));

        //then 직원2 자리정보 검사
        Employee previousOwner = employeeService.getEmployeeEntityById(owner2Id);
        Assert.assertThat(previousOwner.getSeat(), is(nullValue()));

        //then 자리2 직원정보 검사
        SeatResponseDetailsDTO previousSeat = seatService.getSeatById(createdSeat2.getId());
        Assert.assertThat(previousSeat.getEmployee(), is(nullValue()));


    }

    @Test
    public void updateSeatLocationInBulk() {
        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);
        SeatResponseDetailsDTO createdSeat1 = seatService.createSeat(requestForCreate);

        final BigDecimal lat2 = new BigDecimal("122.8512");
        final BigDecimal lng2 = new BigDecimal("11.4554");
        requestForCreate = createSeatRequestDTO(firstFloorId, lat2, lng2);
        SeatResponseDetailsDTO createdSeat2 = seatService.createSeat(requestForCreate);

        final long newFloorId = floorIdList.get(1);
        final BigDecimal newLat = new BigDecimal("11.225");
        final BigDecimal newLng = new BigDecimal("-1.2544");
        SeatUpdateRequestDTO updateRequest1 = createSeatUpdateRequestDTO(createdSeat1.getId(), newFloorId, newLat, newLng);

        final long newFloorId2 = floorIdList.get(1);
        final BigDecimal newLat2 = new BigDecimal("11.225");
        final BigDecimal newLng2 = new BigDecimal("-1.2544");
        SeatUpdateRequestDTO updateRequest2 = createSeatUpdateRequestDTO(createdSeat2.getId(), newFloorId2, newLat2, newLng2);

        List<SeatUpdateRequestDTO> bulkRequest = new ArrayList<>();
        bulkRequest.add(updateRequest1);
        bulkRequest.add(updateRequest2);

        printStartLine();
        List<SeatResponseDetailsDTO> result = seatService.updateSeatInBulk(bulkRequest);
        printEndLine();

        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("coordinate", hasProperty("lat", Matchers.is(closeTo(newLat, new BigDecimal(0.000001))))),
                hasProperty("coordinate", hasProperty("lat", Matchers.is(closeTo(newLat2, new BigDecimal(0.000001)))))
        ));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("floor", hasProperty("floorName", Matchers.is(floorList.get(newFloorId).getFloorName()))),
                hasProperty("floor", hasProperty("floorName", Matchers.is(floorList.get(newFloorId).getFloorName())))
        ));


    }

    @Test
    //  @Transactional
    public void getSeatById() {

        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);

        SeatResponseDTO createdSeat = seatService.createSeat(requestForCreate);

        final long employeeIdToUpdate = employeeIdList.get(1);
        SeatEmployeeUpdateRequestDTO updateRequest = createSeatEmployeeUpdateRequestDTO(employeeIdToUpdate);
        seatService.updateSeatWithEmployee(createdSeat.getId(), updateRequest);

        printStartLine();
        SeatResponseDetailsDTO seat = seatService.getSeatById(createdSeat.getId());
        printEndLine();

        Assert.assertThat(seat.getCoordinate().getLat().doubleValue(), is(lat.doubleValue()));
        Assert.assertThat(seat.getCoordinate().getLng().doubleValue(), is(lng.doubleValue()));
        Assert.assertThat(seat.getFloor().getFloorName(), is(floorList.get(firstFloorId).getFloorName()));
        Assert.assertThat(seat.getFloor().getImagePath(), is(floorList.get(firstFloorId).getImagePath()));

    }

    @Test(expected = SeatNotFoundException.class)
    //   @Transactional
    public void deleteSeat() {

        final long firstFloorId = floorIdList.get(0);
        final BigDecimal lat = new BigDecimal("12.852");
        final BigDecimal lng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, lat, lng);
        SeatResponseDTO createdSeat = seatService.createSeat(requestForCreate);

        final long employeeIdToUpdate = employeeIdList.get(1);
        SeatEmployeeUpdateRequestDTO updateRequest = createSeatEmployeeUpdateRequestDTO(employeeIdToUpdate);
        SeatResponseDetailsDTO updatedSeat = seatService.updateSeatWithEmployee(createdSeat.getId(), updateRequest);


        Employee ownerOfSeat = employeeService.getEmployeeEntityById(employeeIdToUpdate);

        FloorResponseDetailsDTO fistFloor = floorService.getFloorById(firstFloorId);

        Assert.assertThat(ownerOfSeat.getSeat().getId(), is(updatedSeat.getId()));
        Assert.assertThat(fistFloor.getSeats().size(), is(1));

        printStartLine();
        seatService.deleteSeat(createdSeat.getId());
        printEndLine();

        ownerOfSeat = employeeService.getEmployeeEntityById(employeeIdToUpdate);
        fistFloor = floorService.getFloorById(firstFloorId);

        Assert.assertThat(ownerOfSeat.getSeat(), is(nullValue()));
        Assert.assertThat(fistFloor.getSeats().size(), is(0));
        seatService.getSeatById(createdSeat.getId());
    }

    @Test
    // @Transactional
    public void getAllSeats() {
        final long firstFloorId = floorIdList.get(0);
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);
        SeatResponseDetailsDTO createdSeat1=seatService.createSeat(requestForCreate);

//        long owner1Id = employeeIdList.get(0);
//        SeatEmployeeUpdateRequestDTO updateRequest = createSeatEmployeeUpdateRequestDTO(owner1Id);
//         seatService.updateSeatWithEmployee(createdSeat1.getId(), updateRequest);

        final long secondFloorId = floorIdList.get(1);
        final BigDecimal secondLat = new BigDecimal("11.225");
        final BigDecimal secondLng = new BigDecimal("-1.2544");
        requestForCreate = createSeatRequestDTO(secondFloorId, secondLat, secondLng);
        SeatResponseDetailsDTO createdSeat2=seatService.createSeat(requestForCreate);

//            owner1Id = employeeIdList.get(1);
//         updateRequest = createSeatEmployeeUpdateRequestDTO(owner1Id);
//            seatService.updateSeatWithEmployee(createdSeat2.getId(), updateRequest);



        printStartLine();
        List<SeatResponseDetailsDTO> result = seatService.getAllSeats();
        printEndLine();

        Assert.assertThat(result.size(), is(2));

        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("coordinate", hasProperty("lat", Matchers.is(closeTo(firstLat, new BigDecimal(0.000001))))),
                hasProperty("coordinate", hasProperty("lat", Matchers.is(closeTo(secondLat, new BigDecimal(0.000001)))))
        ));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("floor", hasProperty("floorName", Matchers.is(floorList.get(firstFloorId).getFloorName()))),
                hasProperty("floor", hasProperty("floorName", Matchers.is(floorList.get(secondFloorId).getFloorName())))
        ));

        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("floor", hasProperty("imagePath", Matchers.is(floorList.get(firstFloorId).getImagePath()))),
                hasProperty("floor", hasProperty("imagePath", Matchers.is(floorList.get(secondFloorId).getImagePath())))
        ));


    }


    @Test
    //  @Transactional
    public void deleteAllByFloorId() {
        final long firstFloorId = floorIdList.get(0);
        final BigDecimal firstLat = new BigDecimal("12.852");
        final BigDecimal firstLng = new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate = createSeatRequestDTO(firstFloorId, firstLat, firstLng);
        seatService.createSeat(requestForCreate);


        final BigDecimal secondLat = new BigDecimal("11.225");
        final BigDecimal secondLng = new BigDecimal("-1.2544");
        requestForCreate = createSeatRequestDTO(firstFloorId, secondLat, secondLng);
        seatService.createSeat(requestForCreate);

        printStartLine();
        seatService.deleteAllByFloorId(firstFloorId);
        printEndLine();

        List<SeatResponseDetailsDTO> result = seatService.getAllSeats();

        Assert.assertThat(result.size(), is(0));

    }


}

