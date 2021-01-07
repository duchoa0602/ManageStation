package com.manage.station.repository;

import com.manage.station.entity.ValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ValueRepository extends JpaRepository<ValueEntity, Long> {
    @Query("Select v FROM ValueEntity v where v.device.id = :deviceId " +
            " and v.createdDate >= :fromDate  and v.createdDate <= :toDate order by v.createdDate asc")
    List<ValueEntity> getValueByDeviceIdAndTime(@Param("deviceId") Long deviceId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("Select v FROM ValueEntity v, DeviceEntity d where v.device.id = :deviceId " +
            " and v.createdDate >= :fromDate  and v.createdDate <= :toDate " +
            " and v.device.id = d.id and (v.val > d.highValue or v.val < d.lowValue) " +
            " order by v.createdDate asc")
    List<ValueEntity> getValueWarning(@Param("deviceId") Long deviceId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
