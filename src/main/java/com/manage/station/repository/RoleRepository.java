package com.manage.station.repository;

import com.manage.station.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r " +
            "inner join UserRoleEntity ur on r.id = ur.id.roleId " +
            "inner join UserEntity u on ur.id.userId = u.id and u.id = :userId ")
    List<RoleEntity> findAllRoleByUser(@Param("userId") Long userId);


}
