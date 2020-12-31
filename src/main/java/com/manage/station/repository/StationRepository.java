package com.manage.station.repository;

import com.manage.station.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
