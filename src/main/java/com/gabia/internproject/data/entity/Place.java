package com.gabia.internproject.data.entity;



import com.gabia.internproject.dto.response.PlaceResponseDTO;
import com.gabia.internproject.dto.response.PlaceResponseDetailsDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
@Entity
@Getter
@Table(name = "place")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_place_floor_id"),nullable = false)
    private Floor floor;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_place_place_type_id"),nullable = false)
    private PlaceType placeType;

    @Setter
    @Column(nullable = false,length = 20)
    private String placeName;

    @Setter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="lat",
                    column=@Column(name="seat_lat",nullable=false,precision = 18, scale=8)),
            @AttributeOverride(name="lng",
                    column=@Column(name="seat_lng",nullable=false,precision = 18, scale=8))
    })
    Coordinate coordinate;



//
//    @OneToMany(mappedBy ="place",fetch = FetchType.LAZY)
//    List<Reservation> reservations;


    public static Place CreateNewPlace(Floor floor,PlaceType placeType,String placeName,Coordinate coordinate){
       Place newPlace = new Place();
        newPlace.setPlaceType(placeType);
        newPlace.setPlaceName(placeName);
        newPlace.setCoordinate(coordinate);
        newPlace.setFloorAndAddInList(newPlace,floor);
       return newPlace;
    }

    public void updateFloor(Floor newFloor){
        removePlaceFromFloor();
        setFloorAndAddInList(this,newFloor);
    }

    private void setFloorAndAddInList(Place place,Floor newFloor){
        place.setFloor(newFloor);
        newFloor.addPlace(place);
    }

    @PreRemove
    public void remove(){
        removePlaceFromFloor();

    }


    public void removePlaceFromFloor()
    {
        if(this.floor!=null){
            this.floor.getPlaces().remove(this);
        }

    }



}
