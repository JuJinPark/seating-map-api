package com.gabia.internproject.controller;

import com.gabia.internproject.dto.request.PlaceRequestDTO;
import com.gabia.internproject.dto.request.SeatRequestDTO;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.dto.response.PlaceResponseDetailsDTO;
import com.gabia.internproject.dto.response.SeatResponseDTO;
import com.gabia.internproject.service.PlaceService;
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

@RestController
@Validated
//@RequestMapping("/places")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @PostMapping("/places")
    @ApiOperation(value = "새 장소 등록", notes = "새 장소를 등록하는 API.",response = PlaceResponseDetailsDTO.class)
    @ApiResponses(value={@ApiResponse(code = 201, message ="created" , response = PlaceResponseDetailsDTO.class),
            @ApiResponse(code = 200, message ="not used",response = void.class)
    })
    public ResponseEntity<?> createPlace(@RequestBody @Valid PlaceRequestDTO placeRequestDTO) {

        PlaceResponseDetailsDTO result=placeService.createPlace(placeRequestDTO);

        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping("/places")
    @ApiOperation(value = "모든 장소 조회", notes = "전체 장소정보를 조회하는 API.",response = PlaceResponseDetailsDTO.class, responseContainer = "List")
    public ResponseEntity<?> getAllPlaces() {

        return ResponseEntity.ok(placeService.getAllPlaces());

    }

    @GetMapping("/places/{placeId}")
    @ApiOperation(value = "특정 장소 조회", notes = "특정 장소의 정보를 조회",response = PlaceResponseDetailsDTO.class)
    public ResponseEntity<?> getPlaceById(@PathVariable @Min(value = 1,message = "장소 id 가 잘못되었습니다.") long placeId) {
        PlaceResponseDetailsDTO result=placeService.getPlaceById(placeId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/places/{placeId}")
    @ApiOperation(value = "특정 장소 정보 수정", notes = "특정 장소 정보 수정.",response = PlaceResponseDetailsDTO.class)
    public ResponseEntity<?> updatePlace(@Min(value = 1,message = "장소 id 가 잘못되었습니다.") @PathVariable  long placeId,@RequestBody @Valid PlaceRequestDTO placeRequestDTO
    ) {

        PlaceResponseDetailsDTO result=placeService.updatePlace(placeId,placeRequestDTO);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/places/{placeId}")
    @ApiOperation(value = "특정 장소 지우기", notes = "특정 장소를 삭제하는 API.")
    @ApiResponses(value={@ApiResponse(code = 204, message ="deleted" ),
            @ApiResponse(code = 200, message ="not used") })
    public ResponseEntity<?> deletePlace(@PathVariable  @Min(value = 1,message = "장소 id 가 잘못되었습니다.") long placeId) {

        placeService.deletePlace(placeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
