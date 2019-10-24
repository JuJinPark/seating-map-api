//package com.gabia.internproject.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/seat-change-requests")
//@Api(tags = { "자리변경 요청관리(admin)" } )
//public class SeatChangeRequestController {
//
//
//    @GetMapping
//    @ApiOperation(value = "모든 자리변경 요청 조회", notes = "모든 자리변경 요청 API.")
//    public ResponseEntity<?> getAllSeatChangeRequest() {
//
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "특정 직원 조회", notes = "특정 직원정보 조회")
//    public ResponseEntity<?> selectEmployee(@PathVariable int id) {
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//    @PostMapping
//    @ApiOperation(value = "새 직원 등록", notes = "새 직원 등록하는 API.")
//    public ResponseEntity<?> insertEmployee(@RequestBody String userInfo) {
//
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "특정 직원 삭제", notes = "특정 직원을 삭제하는 API.")
//    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//
//    @PutMapping("/{id}/seat")
//    @ApiOperation(value = "특정 자리에 직원 매핑", notes = "직원 자리 정보 등록")
//    public ResponseEntity<?> updateEmployeeSeatNumber(@PathVariable int id,@RequestBody int seatNumber) {
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//
//    @PostMapping("/{id}/seat-change-requests")
//    @ApiOperation(value = "자리변경 요청", notes = "자리변경 요청 api")
//    public ResponseEntity<?> makeSeatChangeRequest(@RequestBody String seatChangeRequest) {
//
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//    @PutMapping("/{id}/seat-change-requests")
//    @ApiOperation(value = "자리변경 요청 수정", notes = "자리변경 요청 수정 api")
//    public ResponseEntity<?> updateSeatChangeRequest(@RequestBody String seatChangeRequest) {
//
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//    @PostMapping("/{id}/meetingroom-reservations")
//    @ApiOperation(value = "회의실 예약 추가", notes = "회의실 예약 추가 api")
//    public ResponseEntity<?> makeMeetingRoomReservation(@RequestBody String reservationInfo) {
//
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//    @PutMapping("/{id}/meetingroom-reservations")
//    @ApiOperation(value = "회의실 예약 수정", notes = "회의실 예약 수정 api")
//    public ResponseEntity<?> updateMeetingRoomReservation(@RequestBody String reservationInfo) {
//
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//
//    @DeleteMapping("/{id}/meetingroom-reservations/{reservation-id}")
//    @ApiOperation(value = "특정 예약 삭제 또는 취소", notes = "특정 예약을 취소 또는 삭제 API.")
//    public ResponseEntity<?> deleteEmployee(@PathVariable int id,@PathVariable int reservationId) {
//        return (ResponseEntity<?>) ResponseEntity.status(200);
//    }
//
//
//
//
//
//
//
//
//
//
//}
