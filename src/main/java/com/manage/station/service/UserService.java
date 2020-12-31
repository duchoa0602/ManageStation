package com.manage.station.service;

import com.manage.station.entity.DeviceEntity;
import com.manage.station.entity.StationEntity;
import com.manage.station.entity.UserEntity;
import com.manage.station.entity.UserStationEntity;

import java.util.List;

public interface UserService {

    UserEntity findUserByUsername(String username);

    void registerNewUser(UserEntity userEntity);

    List<UserStationEntity> findAllStationByUser(String username);

    UserStationEntity findByUserIdAndStationId(Long userId, Long stationId);

    void deleteStationOfUser(Long userId, Long stationId);

    void saveOrUpdateUserStation(UserStationEntity userStationEntity);

    List<DeviceEntity> findAllDeviceByStationIdOfUser(Long stationId);

    StationEntity findStationById(Long stationId);

}
