package com.gabia.internproject.data.entity;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "seat_change_request")
public class SeatChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_seat_id",nullable = false,foreignKey=@ForeignKey(name="fk_seat_change_request_crt_seat_id"))
    private Seat currentSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_seat_id",nullable = false,foreignKey=@ForeignKey(name="fk_seat_change_request_rqst_seat_id"))
    private Seat requestSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,foreignKey=@ForeignKey(name="fk_seat_change_request_employee_id"))
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 10)
    private RequestStatus requestStatus;
}
