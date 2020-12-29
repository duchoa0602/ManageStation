package com.manage.station.repository;

import com.manage.station.entity.UserRoleEntity;
import com.manage.station.entity.id.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {

    @Query("select u from UserRoleEntity u where u.id.userId = :id")
    Optional<UserRoleEntity> findUserRoleByUserId(@Param("id") Long userId);

}
