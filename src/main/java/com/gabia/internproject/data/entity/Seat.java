package com.gabia.internproject.data.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "seat")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,columnDefinition = "bigint(20)")
    private long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_seat_employee_id"))
    private Employee employee;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,foreignKey=@ForeignKey(name="fk_seat_floor_id"))
    private Floor floor;

    @Setter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="lat",
                    column=@Column(name="seat_lat",nullable=false,precision = 18, scale=8)),
            @AttributeOverride(name="lng",
                    column=@Column(name="seat_lng",nullable=false,precision = 18, scale=8))
    })
    private Coordinate coordinate;

    @OneToMany(mappedBy = "seat",fetch = FetchType.LAZY)
    private List<SeatArrangementHistory> seatArrangementHistories;

    public static Seat CreateNewSeat(Floor floor,Coordinate coordinate){
        Seat newSeat= new Seat();
        newSeat.setCoordinate(coordinate);
        newSeat.setFloorAndAddInList(newSeat,floor);
        return newSeat;

    }

    public void updateFloor(Floor newFloor){
        removeSeatFromFloor();
        setFloorAndAddInList(this,newFloor);
    }

    private void setFloorAndAddInList(Seat seat,Floor newFloor){
        seat.setFloor(newFloor);
        newFloor.addSeat(seat);
    }

    public void assignTo(Employee newOwner){
        removeSeatOfNewOwner(newOwner);
        removePreviousOwner();
        setEmployee(newOwner);
        newOwner.setSeat(this);
    }
    public void setEmployee(Employee employee){
        this.employee=employee;

    }

    private void removeSeatOfNewOwner(Employee newOwner){
        if(newOwner.getSeat()!=null){
            newOwner.getSeat().setEmployee(null);
        }
    }

    private void removePreviousOwner(){
        if(this.employee!=null){
            this.employee.setSeat(null);
        }
    }

    public void clearSeat(){
        removePreviousOwner();
        setEmployee(null);
    }


    @PreRemove
    public void remove(){
        removeSeatOfEmployee();
        removeSeatFromFloor();

    }

    private void removeSeatOfEmployee(){
        if(this.employee!=null){
            this.employee.setSeat(null);
        }
    }
    private void removeSeatFromFloor()
    {
            if(this.floor!=null){
                this.floor.getSeats().remove(this);
            }

    }


}
