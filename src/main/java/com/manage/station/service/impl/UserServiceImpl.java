package com.manage.station.service.impl;

import com.manage.station.entity.*;
import com.manage.station.entity.id.UserRoleId;
import com.manage.station.entity.id.UserStationId;
import com.manage.station.repository.*;
import com.manage.station.service.UserService;
import com.manage.station.utils.Const;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final StationRepository stationRepository;
    private final UserStationRepository userStationRepository;
    private final DeviceRepository deviceRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserRoleRepository userRoleRepository,
                           StationRepository stationRepository,
                           UserStationRepository userStationRepository,
                           DeviceRepository deviceRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.stationRepository = stationRepository;
        this.userStationRepository = userStationRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Not found by username"));
    }

    @Override
    public void registerNewUser(UserEntity userEntity) {
        userEntity.setId(null);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity userSaved = userRepository.save(userEntity);

        RoleEntity roleEntity = roleRepository.findById(Const.RoleId.USER).get();

        // insert new role
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setRoleId(roleEntity.getId());
        userRoleId.setUserId(userSaved.getId());

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(userRoleId);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setUser(userSaved);

        userRoleRepository.save(userRoleEntity);
    }

    @Override
    public List<UserStationEntity> findAllStationByUser(String username) {
        return userStationRepository.findAllUserStations(username.trim().toUpperCase());
    }

    @Override
    public UserStationEntity findByUserIdAndStationId(Long userId, Long stationId) {
        return userStationRepository.findByUserIdAndStationId(userId, stationId)
                .orElseThrow(() -> new IllegalArgumentException("Not found by id"));
    }

    @Override
    public void deleteStationOfUser(Long userId, Long stationId) {
        UserStationId userStationId = UserStationId.builder().userId(userId).stationId(stationId).build();
        userStationRepository.deleteById(userStationId);
    }

    @Override
    public void saveOrUpdateUserStation(UserStationEntity userStationEntity) {
        UserEntity userEntity = userRepository.findByUsername(userStationEntity.getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Not found by username " + userStationEntity.getUser().getUsername()));
        StationEntity stationEntity = userStationEntity.getStation();
        StationEntity stationSaved = stationRepository.save(stationEntity);

        userStationEntity.setUser(userEntity);
        userStationEntity.setStation(stationEntity);
        userStationEntity.setId(UserStationId.builder().userId(userEntity.getId()).stationId(stationSaved.getId()).build());

        userStationRepository.save(userStationEntity);
    }

    @Override
    public List<DeviceEntity> findAllDeviceByStationIdOfUser(Long stationId) {
        return deviceRepository.findAllDeviceByStationId(stationId);
    }

    @Override
    public StationEntity findStationById(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new IllegalArgumentException("Not found by id"));
    }
}
