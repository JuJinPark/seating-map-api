package com.gabia.internproject.data.entity;



import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "department")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Department {

    @Id
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    @Setter
    private long id;

    @Setter
    @Column(nullable = false,length = 20)
    private String departmentName;

//    public static Department createNewDepartment(String departmentName){
//        Department dept=new Department();
//        dept.setDepartmentName(departmentName);
//        return dept;
//    }
//




}
