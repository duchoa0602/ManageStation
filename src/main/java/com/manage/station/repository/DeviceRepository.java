package com.manage.station.repository;

import com.manage.station.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    @Query("Select d FROM DeviceEntity d where d.station.id = :stationId " +
            "order by d.name asc")
    List<DeviceEntity> findAllDeviceByStationId(@Param("stationId") Long stationId);
}
