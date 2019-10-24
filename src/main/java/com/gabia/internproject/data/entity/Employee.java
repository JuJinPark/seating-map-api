package com.gabia.internproject.data.entity;



import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;


    @Column(name = "employee_api_id",nullable = false)
    private int employeeApiId;

    @Setter
    @Column(nullable = false, length = 30)
    private String employeeName;

    @Setter
    @Column(nullable = false, length = 320)
    private String companyEmail;

    private int employeeExtension;

    @Setter
    @Column(nullable = false, length = 20)
    private String employeeNumber;


    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_employee_department_id"))
    private Department department;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_employee_role_id"))
    private Role role;

    @OneToOne(mappedBy = "employee",fetch = FetchType.LAZY)
    private Seat seat;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    private List<SocialLoginEmail> socialLoginEmails;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    private List<SeatArrangementHistory> seatArrangementHistories;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    private List<SeatChangeRequest>  seatChangeRequests;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    private List<Reservation> reservations;



//
//    public static Employee CreateNewEmployee(String employeeName,String companyEmail,String employeeNumber){
//        Employee newEmployee= new Employee();
//        newEmployee.setEmployeeName(employeeName);
//        newEmployee.setCompanyEmail(companyEmail);
//        newEmployee.setEmployeeNumber(employeeNumber);
//        return newEmployee;
//
//    }

    public void setSeat(Seat seat){
        this.seat=seat;
    }

//    @PreRemove
//    public void removeEmployeeOfSeat(){
//        if(this.seat!=null){
//            this.seat.setEmployee(null);
//        }
//    }
//



}
