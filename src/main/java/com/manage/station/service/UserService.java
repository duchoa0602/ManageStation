package com.manage.station.service;

import com.manage.station.entity.*;

import java.sql.Date;
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

    void saveOrUpdateDeviceStation(DeviceEntity deviceEntity);

    DeviceEntity findDeviceById(Long deviceId);

    void deleteDeviceById(Long deviceId);

    List<ValueEntity> getValueByDeviceIdAndTime(Long deviceId, Date fromDate, Date toDate);

    List<ValueEntity> getValueWarningByDeviceIdAndTime(Long deviceId, Date fromDate, Date toDate);

    void saveValue(ValueEntity valueEntity);

    DeviceEntity getDeviceById(Long deviceId);
}
