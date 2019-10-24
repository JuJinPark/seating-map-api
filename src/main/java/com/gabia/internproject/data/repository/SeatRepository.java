package com.gabia.internproject.data.repository;

import com.gabia.internproject.data.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface SeatRepository extends JpaRepository<Seat, Long> {


    @Override
    @Query("select DISTINCT s from Seat s join fetch s.floor left join fetch s.employee e left join fetch e.role left join fetch e.department")
    List<Seat> findAll();

    @Transactional
    @Modifying
    @Query("delete from Seat c where c.floor.id=:id")
    void deleteAllByFloorIdInQuery(@Param("id") long id);

    @Transactional
    @Override
    @Query("select s from Seat s join fetch s.floor where s.id=:id")
    Optional<Seat> findById(@Param("id")Long id);
}
