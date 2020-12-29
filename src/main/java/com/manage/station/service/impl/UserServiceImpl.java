package com.manage.station.service.impl;

import com.manage.station.entity.RoleEntity;
import com.manage.station.entity.UserEntity;
import com.manage.station.entity.UserRoleEntity;
import com.manage.station.entity.id.UserRoleId;
import com.manage.station.repository.RoleRepository;
import com.manage.station.repository.UserRepository;
import com.manage.station.repository.UserRoleRepository;
import com.manage.station.service.UserService;
import com.manage.station.utils.Const;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
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
}
