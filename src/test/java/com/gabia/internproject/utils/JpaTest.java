package com.gabia.internproject.utils;

import com.gabia.internproject.data.entity.*;
import com.gabia.internproject.data.repository.*;
import com.gabia.internproject.dto.CoordinateDTO;
import com.gabia.internproject.dto.request.SeatEmployeeUpdateRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.response.SeatResponseDTO;
import com.gabia.internproject.service.FloorService;
import com.gabia.internproject.service.SeatService;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil2;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JpaTest {


    @Autowired
    SeatRepository seatRepository;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    FloorService floorService;

    @Autowired
    SeatService seatService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PlaceTestRepository placeTestRepository;

    public SeatRequestDTO createSeatRequestDTO(long floorId, BigDecimal lat, BigDecimal lng){
        CoordinateDTO cor=new CoordinateDTO();
        cor.setLat(lat);
        cor.setLng(lng);

        SeatRequestDTO request=new SeatRequestDTO();
        request.setFloorId(floorId);
        request.setCoordinate(cor);

        return request;
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    @Transactional
    public void jpaCreateSeatTest(){

        Floor floor=Floor.createNewFloor("4층","/4th");
        Seat seat= Seat.CreateNewSeat(floor,new Coordinate(new BigDecimal("12.852"),new BigDecimal("-1111.44")));
        seatRepository.save(seat);
        //seat 에 cascade 옵션 필요 그러면 새로 만든다 아니면 excpetion 문제 일어남

    }

    @Test
    @Transactional
    public void jpaCheckSeatListwithouRemovingManually() {
        Floor first=  Floor.createNewFloor("4층","/4thFloor"); Floor second= Floor.createNewFloor("5층","/5thFloor");
        first=floorRepository.save(first);


        final BigDecimal firstLat=new BigDecimal("12.852");
        final BigDecimal firstLng=new BigDecimal("-1111.44");
        final Coordinate cor=new Coordinate(firstLat,firstLng);


        Seat newSeat=Seat.CreateNewSeat(first,cor);
        seatRepository.save(newSeat);
        newSeat=Seat.CreateNewSeat(first,cor);
        seatRepository.save(newSeat);

        List<Seat> seats=first.getSeats();

        Assert.assertThat(seats.size(),is(2));
        Assert.assertThat(seats.get(0).getCoordinate().getLat().doubleValue(), is(firstLat.doubleValue()));
        Assert.assertThat(seats.get(0).getCoordinate().getLng().doubleValue(), is(firstLng.doubleValue()));
        Assert.assertThat(seats.get(0).getFloor().getFloorName(), is(first.getFloorName()));
        Assert.assertThat(seats.get(0).getFloor().getImagePath(), is(first.getImagePath()));

        //seat 만들어질때 list add 안해주면 getSeats안됨.

        //transaction 내에서 eager로 해도 쿼리가 다르다 transaction 안주면 getseat에서 조인되서 보인다
        //Lazy로 했을떄도 조인된게 안보임

    }


//    @Test
//    @Transactional
    public void changesInListInSameTransaction() {
        //given
        Floor first=  Floor.createNewFloor("4층","/4thFloor");
        first=floorRepository.save(first);


        final BigDecimal firstLat=new BigDecimal("12.852");
        final BigDecimal firstLng=new BigDecimal("-1111.44");
        final Coordinate cor=new Coordinate(firstLat,firstLng);
        Seat newSeat=Seat.CreateNewSeat(first,cor);
        seatRepository.save(newSeat);
        Seat newSeat2=Seat.CreateNewSeat(first,cor);
        seatRepository.save(newSeat2);

        //when
        Floor floor=floorRepository.findById(1L).orElse(null);
        List<Seat> seatsOfFloor=floor.getSeats();
        List<Seat> seatsFromSeatTable=seatRepository.findAll();

        //then
        Assert.assertThat(seatsOfFloor.size(),is(0));
        Assert.assertThat(seatsFromSeatTable.size(),is(2));


    }

    @Test
    @Transactional
    public void jpaTestFlushTimeForInsertAndInsert() throws InterruptedException {


        final BigDecimal firstLat=new BigDecimal("12.852");
        final BigDecimal firstLng=new BigDecimal("-1111.44");
        final Coordinate cor=new Coordinate(firstLat,firstLng);
        PlaceTest place = new PlaceTest(0,null,null,"s",cor,null);
        placeTestRepository.save(place);

        Floor first=  Floor.createNewFloor("4층","/4thFloor");
        first=floorRepository.save(first);
        PlaceTest place2 = new PlaceTest(1,null,null,"s",cor,null);
        placeTestRepository.save(place2);
        Floor second=  Floor.createNewFloor("4층","/4thFloor");
        floorRepository.save(second);


    }
    @Test
    @Transactional
    @Rollback(false)
    public void jpaTestFlushTimeForInsertAndDelete() throws InterruptedException {

        Floor first=  Floor.createNewFloor("4층","/4thFloor");
        first=floorRepository.save(first);
        floorRepository.deleteById(first.getId());


        Floor second=  Floor.createNewFloor("4층","/4thFloor");
        floorRepository.save(second);
        floorRepository.deleteById(second.getId());

        Floor third=  Floor.createNewFloor("4층","/4thFloor");
        floorRepository.save(third);

    //    floorRepository.findAll();

    }

    @Test
    @Transactional
    public void updateInSameTransaction() {
        Floor first=  Floor.createNewFloor("4층","/4thFloor");
        Floor second= Floor.createNewFloor("5층","/5thFloor");
        Floor firstInDB=floorRepository.save(first);


        final BigDecimal firstLat=new BigDecimal("12.852");
        final BigDecimal firstLng=new BigDecimal("-1111.44");
        final Coordinate cor=new Coordinate(firstLat,firstLng);


        Seat newSeat=Seat.CreateNewSeat(first,cor);
        Seat newSeatInDB=seatRepository.save(newSeat);
        Seat newSeat2=Seat.CreateNewSeat(first,cor);
        Seat newSeat2InDB=seatRepository.save(newSeat2);

        newSeatInDB.getCoordinate().setLat(firstLng);
        newSeat2InDB.getCoordinate().setLat(firstLng);

        List<Seat> seats=firstInDB.getSeats();
        Assert.assertThat(seats.get(0).getCoordinate().getLat().doubleValue(),is(firstLng.doubleValue()));
        Assert.assertThat(seats.get(1).getCoordinate().getLat().doubleValue(),is(firstLng.doubleValue()));
        //영속성 유지된상태에서는 객체를 변경해도 같은 객체이기떄문에 자동 반영

    }


    @Test
 //   @Transactional
    public void deleteInSameTransaction() {
        //자리와 직원중 직원이 삭제 될시
        //삭제시 관계를 null해주는게 없으시 DataIntegrityViolationException 발생으로 삭제 불가
        Floor first=  Floor.createNewFloor("4층","/4thFloor"); Floor second= Floor.createNewFloor("5층","/5thFloor");
        Floor firstInDB=floorRepository.save(first);


        final BigDecimal firstLat=new BigDecimal("12.852");
        final BigDecimal firstLng=new BigDecimal("-1111.44");
        final Coordinate cor=new Coordinate(firstLat,firstLng);


        Seat newSeat=Seat.CreateNewSeat(first,cor);
        newSeat=seatRepository.save(newSeat);
//
//        Employee employee1= Employee.CreateNewEmployee("익명1","gabialeader.com","mm123");
//        employee1=employeeRepository.save(employee1);
//
//        newSeat.assignTo(employee1);

//        employeeRepository.deleteById(employee1.getId());

//        List<Employee> employees=employeeRepository.findAll();
//
//        Assert.assertThat(employees.size(),is(0));
//         Assert.assertThat(newSeat.getEmployee(),is(nullValue()));
    //   seatService.deleteSeat(newSeat.getId());
        Integer id=((Long)firstInDB.getId()).intValue();
        floorService.deleteFloor(firstInDB.getId());
      //  floorRepository.deleteById(firstInDB.getId());

  //      Floor floor2=floorRepository.findById(first.getId()).orElse(null);
//       Seat seat2=seatRepository.findById(newSeat.getId()).orElse(null);
//  // floorRepository.findById(first.getId());
//   Assert.assertThat(seat2,is(nullValue()));
       // Assert.assertThat(floor2.getImagePath(),is(nullValue()));

    }

//    @Test
//  @Transactional
    public void deleteInSameTransactionBetweenFloorAndSeat() {
        //자리와 층 삭제시 층을 삭제하면 자식과읜 관계를 먼저 정리해줘야함
        //  null 로 자식과의 관계를 정리해주고 cascade remove 또는 orpahremove true 해결 할수있다.
        //삭제시 관계를 null해주는게 없으시 DataIntegrityViolationException 발생으로 삭제 불가
        Floor first=  Floor.createNewFloor("4층","/4thFloor");
        Floor second= Floor.createNewFloor("5층","/5thFloor");
        first=floorRepository.save(first);
      //  second=floorRepository.save(second);

        final BigDecimal firstLat=new BigDecimal("12.852");
        final BigDecimal firstLng=new BigDecimal("-1111.44");
        final Coordinate cor=new Coordinate(firstLat,firstLng);


        Seat newSeat=Seat.CreateNewSeat(first,cor);
        newSeat=seatRepository.save(newSeat);
        seatRepository.save(Seat.CreateNewSeat(first,cor));

      //  Employee employee1= Employee.CreateNewEmployee("익명1","gabialeader.com","mm123");
        Employee employee1= new Employee();
        employee1.setEmployeeName("익명1");
        employee1.setCompanyEmail("gabialeader.com");
        employee1.setEmployeeNumber("mm123");

        employee1=employeeRepository.save(employee1);

        newSeat.assignTo(employee1);

        Assert.assertThat(first.getSeats().size(),is(2));
        floorService.deleteFloor(first.getId());


        List<Floor> list=floorRepository.findAll();
        Assert.assertThat(list.size(),is(0));
        Assert.assertThat(employee1.getSeat(),is(nullValue()));


    }

    @Test(expected = DataIntegrityViolationException.class )
    public void deleteInDifferentTransaction() {

        //삭제시 관계를 null해주는게 없으시 DataIntegrityViolationException 발생으로 삭제 불가
        Floor first=  Floor.createNewFloor("4층","/4thFloor"); Floor second= Floor.createNewFloor("5층","/5thFloor");
        Floor firstInDB=floorRepository.save(first);


        final BigDecimal firstLat=new BigDecimal("12.852");
        final BigDecimal firstLng=new BigDecimal("-1111.44");
        final Coordinate cor=new Coordinate(firstLat,firstLng);


        Seat newSeat=Seat.CreateNewSeat(first,cor);
        newSeat=seatRepository.save(newSeat);

      //  Employee employee1= Employee.CreateNewEmployee("익명1","gabialeader.com","mm123");
        Employee employee1= new Employee();
        employee1.setEmployeeName("익명1");
        employee1.setCompanyEmail("gabialeader.com");
        employee1.setEmployeeNumber("mm123");

        employee1=employeeRepository.save(employee1);

        SeatEmployeeUpdateRequestDTO updateRequest=new SeatEmployeeUpdateRequestDTO();
        updateRequest.setEmployeeId(employee1.getId());

        seatService.updateSeatWithEmployee(newSeat.getId(),updateRequest);
        employeeRepository.deleteById(employee1.getId());
        Seat newSeatFromDB=seatRepository.findById(newSeat.getId()).orElse(null);

        Assert.assertThat(newSeatFromDB.getEmployee(),is(nullValue()));



    }

//    @Test
    public void changesInListsInDifferentTransactions(){
        Floor first= Floor.createNewFloor("4층","/4thFloor");
        first=floorRepository.save(first);


        final BigDecimal lat=new BigDecimal("12.852");
        final BigDecimal lng=new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate=createSeatRequestDTO(first.getId(),lat,lng);
        SeatResponseDTO createdSeat= seatService.createSeat(requestForCreate);



        Floor firstFromDB=floorRepository.findById(first.getId()).orElse(null);
        List<Seat> seats=firstFromDB.getSeats();
        Assert.assertThat(seats.size(),is(1));
        seatService.deleteSeat(createdSeat.getId());
        Floor firstFromDB2=floorRepository.findById(first.getId()).orElse(null);
        List<Seat> seats2=firstFromDB2.getSeats();
        Assert.assertThat(seats2.size(),is(0));
        //trasanction이 다를떄 즉 영속성 컨텍스가 다를때는 list와 같은 곳에 add 또는 remove를 해주지 않아도 된다.
        //하지만 제대로 repository에서 얻어와야함.
    }

//    @Test
    public void updateInDifferentTransactions(){
        Floor first= Floor.createNewFloor("4층","/4thFloor");
        first=floorRepository.save(first);


        final BigDecimal lat=new BigDecimal("12.852");
        final BigDecimal lng=new BigDecimal("-1111.44");
        SeatRequestDTO requestForCreate=createSeatRequestDTO(first.getId(),lat,lng);
        SeatResponseDTO createdSeat= seatService.createSeat(requestForCreate);

        final BigDecimal newLat=new BigDecimal("-1111.44");
        final BigDecimal newLng=new BigDecimal("-1111.44");

        SeatRequestDTO requestForUpdate=createSeatRequestDTO(first.getId(),newLat,newLng);
        seatService.updateSeat(createdSeat.getId(),requestForUpdate);

        Floor firstFromDB=floorRepository.findById(first.getId()).orElse(null);
        List<Seat> seats=firstFromDB.getSeats();
        Assert.assertThat(seats.get(0).getCoordinate().getLat().doubleValue(),is(newLat.doubleValue()));

        //다른 transaction단위 즉 다른 영속성단위에서 무슨짓을 하든 repository에서 가지고오면 반영되어있다.

    }




    @Test
    @Transactional
    public void jpaCheckEntityFromSaveAndFindById() {

        Floor first=  Floor.createNewFloor("4층","/4thFloor");

        first=floorRepository.save(first);


        Floor firstFromDB=floorRepository.findById(first.getId()).orElse(null);


        Assert.assertThat(first,is(firstFromDB));

        //trasaction이 유지 된 상태에서는 동일 객체로 본다.
    }

    @Test
    //@Transactional
    public void jpaCheckEntityFromSaveAndFindById2() {

        Floor first=  Floor.createNewFloor("4층","/4thFloor");
        first=floorRepository.save(first);
        Floor firstFromDB=floorRepository.findById(first.getId()).orElse(null);

        first.setImagePath("5th");
        Assert.assertThat(firstFromDB.getImagePath(),not("5th"));

        firstFromDB.setImagePath("6th");


        Floor floorAfterUpdate=floorRepository.findById(first.getId()).orElse(null);

        Assert.assertThat(floorAfterUpdate.getImagePath(),not(firstFromDB.getImagePath()));
        Assert.assertThat(floorAfterUpdate.getImagePath(),not(first.getImagePath()));

        //transaction 없이는 다른객체로 반환되고 변경도 적용이 안된다. 영속성 밖에서 진행했음으로.

    }



}
