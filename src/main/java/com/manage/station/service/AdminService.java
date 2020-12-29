package com.manage.station.service;


import com.manage.station.entity.RoleEntity;
import com.manage.station.entity.UserRoleEntity;

import java.util.List;

public interface AdminService {

    List<RoleEntity> getAllRole();

    List<UserRoleEntity> getAllUserRoles();

    void insertNewUserRole(UserRoleEntity userRoleEntity);

    void updateUserRole(UserRoleEntity input);

    void deleteUserPermission(Long userId);


}
