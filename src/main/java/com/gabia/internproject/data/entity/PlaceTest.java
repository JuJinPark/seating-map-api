package com.gabia.internproject.data.entity;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "placeTest")
@AllArgsConstructor
@NoArgsConstructor
public class PlaceTest {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_place_test_floor_id"))
    private Floor floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_place_test_place_type_id"))
    private PlaceType placeType;

    @Column(nullable = false,length = 20)
    private String placeName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="lat",
                    column=@Column(name="seat_lat",nullable=false,precision = 18, scale=8)),
            @AttributeOverride(name="lng",
                    column=@Column(name="seat_lng",nullable=false,precision = 18, scale=8))
    })
    Coordinate coordinate;


    @OneToMany(mappedBy ="place",fetch = FetchType.LAZY)
    List<Reservation> reservations;





}
