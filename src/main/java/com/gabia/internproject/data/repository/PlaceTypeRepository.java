package com.gabia.internproject.data.repository;

import com.gabia.internproject.data.entity.Place;
import com.gabia.internproject.data.entity.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {

}
