package com.gabia.internproject.data.entity;



import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,foreignKey=@ForeignKey(name="fk_reservation_place_id"))
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,foreignKey=@ForeignKey(name="fk_reservation_employee_id"))
    private Employee employee;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
    private LocalDateTime startTime;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
    private LocalDateTime endTime;






}
