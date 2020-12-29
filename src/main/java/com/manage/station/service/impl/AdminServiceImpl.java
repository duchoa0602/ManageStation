package com.manage.station.service.impl;

import com.manage.station.entity.RoleEntity;
import com.manage.station.entity.UserEntity;
import com.manage.station.entity.UserRoleEntity;
import com.manage.station.entity.id.UserRoleId;
import com.manage.station.repository.RoleRepository;
import com.manage.station.repository.UserRepository;
import com.manage.station.repository.UserRoleRepository;
import com.manage.station.service.AdminService;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            UserRoleRepository userRoleRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<RoleEntity> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public List<UserRoleEntity> getAllUserRoles() {
        Sort mSort = Sort.by(Sort.Order.asc("user.fullName"));
        return userRoleRepository.findAll(mSort);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertNewUserRole(UserRoleEntity userRoleEntity) {
        UserEntity userEntity = userRoleEntity.getUser();
        userEntity.setId(null);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity userSaved = userRepository.save(userEntity);

        RoleEntity roleEntity = roleRepository.findById(userRoleEntity.getRole().getId()).get();
        userRoleEntity.setUser(userSaved);
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setId(UserRoleId.builder().userId(userSaved.getId()).roleId(roleEntity.getId()).build());

        userRoleRepository.save(userRoleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRole(UserRoleEntity input) {
        UserRoleEntity userRoleEntityDelete = userRoleRepository.findUserRoleByUserId(input.getId().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Not found by user id " + input.getId().getUserId()));
        userRoleRepository.delete(userRoleEntityDelete);

        UserEntity userEntity = userRepository.findById(input.getId().getUserId()).get();
        RoleEntity roleEntity = roleRepository.findById(input.getId().getRoleId()).get();
        input.setUser(userEntity);
        input.setRole(roleEntity);

        userRoleRepository.save(input);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserPermission(Long userId) {
        UserRoleEntity userRoleEntity = userRoleRepository.findUserRoleByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Not found by id " + userId));
        userRoleRepository.delete(userRoleEntity);
        userRepository.deleteById(userId);
    }
}
