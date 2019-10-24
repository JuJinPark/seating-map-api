package com.gabia.internproject.data.entity;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "seat_arrangement_history")
public class SeatArrangementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,foreignKey=@ForeignKey(name="fk_seat_arrangement_history_employee_id"))
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,foreignKey=@ForeignKey(name="fk_seat_arrangement_history_seat_id"))
    private Seat seat;

    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
   private LocalDate startDate;


//    @Temporal(TemporalType.DATE)
   private LocalDate endDate;

    @Column(nullable = false, length=30)
   private String employeeName;

    @Column(nullable = false, length = 320)
   private String companyEmail;

    @Column(nullable = true)
   private int employeeExtension;






}
