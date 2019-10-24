package com.gabia.internproject.controller;

import com.gabia.internproject.dto.request.SeatEmployeeUpdateRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.request.SeatUpdateRequestDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.dto.response.SeatResponseDTO;
import com.gabia.internproject.dto.response.SeatResponseDetailsDTO;
import com.gabia.internproject.service.SeatService;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@Validated
//@RequestMapping("/seats")
//@Api(tags = { "자리 등록 및 매핑" } )
public class SeatController {
    @Autowired
   SeatService seatService;



    @GetMapping("/seats/employees")
    @ApiOperation(value = "모든 자리 조회", notes = "전체 자리정보를 조회하는 API.",response = SeatResponseDetailsDTO.class,responseContainer = "List")
    public ResponseEntity<?> getAllSeats() {


        return ResponseEntity.ok(seatService.getAllSeats());

    }

    @PostMapping("/seats")
    @ApiOperation(value = "새 자리 등록", notes = "새 자리를 등록하는 API.",response = SeatResponseDetailsDTO.class)
    @ApiResponses(value={@ApiResponse(code = 201, message ="created" , response = SeatResponseDetailsDTO.class),
            @ApiResponse(code = 200, message ="not used",response = void.class)
    })
    public ResponseEntity<?> createSeat(@RequestBody @Valid SeatRequestDTO seatRequestDTO) {

        SeatResponseDTO result=seatService.createSeat(seatRequestDTO);

        return new ResponseEntity(result, HttpStatus.CREATED);
    }


    @PatchMapping("/seats/{seatId}")
    @ApiOperation(value = "특정 자리 정보 수정", notes = "특정 자리 정보 수정.",response = SeatResponseDetailsDTO.class)
    public ResponseEntity<?> updateSeat(@Min(value = 1,message = "자리 id 가 잘못되었습니다.") @PathVariable  long seatId,@RequestBody @Valid SeatRequestDTO seatRequestDTO
                                        ) {

        //SeatRequestDTO seatRequestDTO=new SeatRequestDTO();
        SeatResponseDTO result=seatService.updateSeat(seatId,seatRequestDTO);

        return ResponseEntity.ok(result);
    }



    @PatchMapping("/seats/bulk")
    @ApiOperation(value = "대량 자리 정보 수정", notes = "대량 자리 정보 수정.",response = SeatResponseDetailsDTO.class,responseContainer = "List")
    public ResponseEntity<?> updateSeatInBulk(@RequestBody @Valid List<SeatUpdateRequestDTO> list)
    {

        List<SeatResponseDetailsDTO> result=seatService.updateSeatInBulk(list);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/seats/{seatId}/employee")
    @ApiOperation(value = "자리와 직원정보 매핑", notes = "자리와 직원 매핑",response = SeatResponseDetailsDTO.class)
    public ResponseEntity<?> updateSeatWithEmployee(@PathVariable @Min(value = 1,message = "자리 id 가 잘못되었습니다.")long seatId
            ,@RequestBody @Valid SeatEmployeeUpdateRequestDTO employeeId) {

        SeatResponseDTO result=seatService.updateSeatWithEmployee(seatId,employeeId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/seats/{seatId}")
    @ApiOperation(value = "특정 자리 조회", notes = "특정 자리의 정보를 조회",response = SeatResponseDetailsDTO.class)
    public ResponseEntity<?> getSeatById(@PathVariable @Min(value = 1,message = "자리 id 가 잘못되었습니다.") long seatId) {
        SeatResponseDTO result=seatService.getSeatById(seatId);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/seats/{seatId}")
    @ApiOperation(value = "특정 자리 지우기", notes = "특정 자리를 삭제하는 API.")
    @ApiResponses(value={@ApiResponse(code = 204, message ="deleted" ),
            @ApiResponse(code = 200, message ="not used") })
    public ResponseEntity<?> deleteSeat(@PathVariable  @Min(value = 1,message = "자리 id 가 잘못되었습니다.") long seatId) {

        seatService.deleteSeat(seatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }









}
