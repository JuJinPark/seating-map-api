package com.gabia.internproject.controller;


import com.gabia.internproject.data.entity.Floor;
import com.gabia.internproject.dto.request.FloorRequestDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.dto.response.FloorResponseDetailsDTO;
import com.gabia.internproject.service.FloorService;
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

@RestController
@Validated
//@RequestMapping("/floors")
public class FloorController {


    FloorService floorService;

    FloorController(FloorService floorService){
        this.floorService=floorService;
    }

    @GetMapping("/floors")
    @ApiOperation(value = "모든 층 조회", notes = "전체 층정보를 조회하는 API.",response = FloorResponseDTO.class, responseContainer = "List")
    public ResponseEntity<?> getAllFloors() {


        return ResponseEntity.ok(floorService.getAllFloors());

    }

    @PostMapping("/floors")
    @ApiOperation(value = "새 층 등록", notes = "새 층을 등록하는 API.")
    @ApiResponses(value={@ApiResponse(code = 201, message ="created" , response = FloorResponseDTO.class),
            @ApiResponse(code = 200, message ="not used",response = void.class)
    })

    public ResponseEntity<?> createFloor(@RequestBody @Valid FloorRequestDTO floorRequestDTO){

        FloorResponseDTO result=floorService.createFloor(floorRequestDTO);

        return new ResponseEntity(result, HttpStatus.CREATED);

    }

    @GetMapping("/floors/{floorId}")
    @ApiOperation(value = "특정 층 조회", notes = "특정 층의 정보를 조회",response = FloorResponseDetailsDTO.class)
    public ResponseEntity<?> getFloorById(@PathVariable @Min(value = 1,message = "층 id 가 잘못되었습니다.") long floorId) {
        FloorResponseDetailsDTO result=floorService.getFloorById(floorId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/floors/{floorId}")
    @ApiOperation(value = "특정 층 정보 수정", notes = "특정 층 정보 수정.",response = FloorResponseDetailsDTO.class)
    public ResponseEntity<?> updateFloor(@Min(value = 1,message = "층 id 가 잘못되었습니다.") @PathVariable  long floorId,@RequestBody @Valid FloorRequestDTO floorRequestDTO
    ) {


        FloorResponseDetailsDTO result=floorService.updateFloor(floorId,floorRequestDTO);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/floors/{floorId}")
    @ApiOperation(value = "특정 층 지우기", notes = "특정 층을 삭제하는 API.")
    @ApiResponses(value={@ApiResponse(code = 204, message ="deleted" ),
            @ApiResponse(code = 200, message ="not used") })
    public ResponseEntity<?> deleteFloor(@PathVariable  @Min(value = 1,message = "층 id 가 잘못되었습니다.") long floorId) {

        floorService.deleteFloor(floorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
