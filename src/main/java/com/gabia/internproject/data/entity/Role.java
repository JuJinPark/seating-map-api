package com.gabia.internproject.data.entity;



import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "role")
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @Setter
    @Column(nullable = false,length = 10)
    private String roleType;

    public static Role createNewRole(String roleType){
        Role role= new Role();
        role.setRoleType(roleType);
        return role;
    }




}
