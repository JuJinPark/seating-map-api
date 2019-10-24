package com.gabia.internproject.data.repository;

import com.gabia.internproject.data.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface FloorRepository extends JpaRepository<Floor, Long> {

//    @Override
//    @Query("select DISTINCT f from Floor f join fetch f.seats join fetch f.places")
//    List<Floor> findAll();

//    @Override
//    @Query("select DISTINCT f from Floor f join fetch f.seats join fetch f.places")
//    Optional<Floor> findById(Long id);

    void deleteByFloorName(String floorName);

}
