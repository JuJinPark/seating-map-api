package com.gabia.internproject.data.entity;



import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "place_type")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class PlaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @Setter
    @Column(nullable = false,length = 20)
    private String typeName;





}
