package com.gabia.internproject.data.repository;

import com.gabia.internproject.data.entity.Place;
import com.gabia.internproject.data.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Transactional
    @Modifying
    @Query("delete from Place p where p.floor.id=:id")
    void deleteAllByFloorIdInQuery(@Param("id") long id);

    @Transactional
    @Override
    @Query("select DISTINCT p from Place p join fetch p.floor join fetch p.placeType")
    List<Place> findAll();

    @Transactional
    @Override
    @Query("select p from Place p join fetch p.floor join fetch p.placeType where p.id=:id")
    Optional<Place> findById(@Param("id")Long id);

//    @Override
//    @Modifying
//    @Query("delete from Place p where p.id=id")
//    void deleteById(@Param("id")Long id);
}
