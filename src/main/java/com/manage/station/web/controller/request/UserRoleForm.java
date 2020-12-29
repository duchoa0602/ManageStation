package com.manage.station.web.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleForm implements Serializable {

    private Long roleId;

    private String username;

    private String password;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String address;

    private String description;
}
