package com.manage.station.web.controller;

import com.manage.station.entity.RoleEntity;
import com.manage.station.entity.UserEntity;
import com.manage.station.entity.UserRoleEntity;
import com.manage.station.entity.id.UserRoleId;
import com.manage.station.service.AdminService;
import com.manage.station.web.controller.request.UserRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/manage-user")
    String manageUser(Model model) {

        return "admin/manage-user";
    }

    @GetMapping("/manage-request")
    String manageRequest(Model model) {

        return "admin/manage-request";
    }

    @GetMapping("/report")
    String report(Model model) {

        return "admin/report";
    }

    @GetMapping("/manage-users")
    String getAllUsers(Model model) {
        model.addAttribute("allUserRoles", adminService.getAllUserRoles());
        model.addAttribute("roles", adminService.getAllRole());
        model.addAttribute("roleIdChangeSelected", null);
        return "admin/users";
    }

    @RequestMapping(value = "/manage-users/new", method = RequestMethod.POST)
    String addUser(UserRoleForm userRoleForm) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(UserEntity.builder()
                .password(userRoleForm.getPassword())
                .email(userRoleForm.getEmail().trim())
                .address(userRoleForm.getAddress().trim())
                .fullName(userRoleForm.getFullName().trim())
                .phoneNumber(userRoleForm.getPhoneNumber().trim())
                .description(userRoleForm.getDescription().trim())
                .username(userRoleForm.getUsername().trim().toLowerCase())
                .build());
        userRoleEntity.setRole(RoleEntity.builder()
                .id(userRoleForm.getRoleId())
                .build());

        adminService.insertNewUserRole(userRoleEntity);
        return "redirect:/admin/manage-users";
    }

    @PostMapping(value = "/manage-users/update")
    String updateUser(Long userId, @Valid @ModelAttribute("roleIdChangeSelected") Long roleIdChangeSelected) {
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(UserRoleId.builder().userId(userId).roleId(roleIdChangeSelected).build())
                .build();
        adminService.updateUserRole(userRoleEntity);
        return "redirect:/admin/manage-users";
    }

    @RequestMapping(value = "/manage-users/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUser(Long userId) {
        adminService.deleteUserPermission(userId);
        return "redirect:/admin/manage-users";
    }

}
