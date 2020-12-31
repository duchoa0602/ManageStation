package com.manage.station.repository;

import com.manage.station.entity.UserStationEntity;
import com.manage.station.entity.id.UserStationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStationRepository extends JpaRepository<UserStationEntity, UserStationId> {

    @Query("SELECT us FROM UserEntity u " +
            "inner join UserStationEntity us on u.id = us.id.userId and upper(u.username) = :username " +
            "inner join StationEntity s on us.id.stationId = s.id " +
            "order by s.name asc ")
    List<UserStationEntity> findAllUserStations(@Param("username") String username);

    @Query("SELECT us FROM UserEntity u " +
            "inner join UserStationEntity us on u.id = us.id.userId and us.id.userId = :userId " +
            "inner join StationEntity s on us.id.stationId = s.id and us.id.stationId = :stationId  ")
    Optional<UserStationEntity> findByUserIdAndStationId(@Param("userId") Long userId,
                                                         @Param("stationId") Long stationId);

}
