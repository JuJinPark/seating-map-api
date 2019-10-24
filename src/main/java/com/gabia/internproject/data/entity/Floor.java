package com.gabia.internproject.data.entity;



import com.gabia.internproject.data.repository.SeatRepository;
import com.gabia.internproject.dto.response.FloorResponseDTO;
import com.gabia.internproject.dto.response.FloorResponseDetailsDTO;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "floor")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;

    @Setter
    @Column(nullable = false,length = 30)
    private String floorName;

    @Setter
    @Lob
    private String imagePath;

    @OneToMany(mappedBy = "floor",fetch = FetchType.LAZY)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "floor",fetch = FetchType.LAZY)
    private List<Place> places = new ArrayList<>();



    public static Floor createNewFloor(String floorName,String imagePath){
        Floor newFloor= new Floor();
        newFloor.setFloorName(floorName);
        newFloor.setImagePath(imagePath);
        return newFloor;

    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }

    public void addPlace(Place place){
        places.add(place);
    }



}
