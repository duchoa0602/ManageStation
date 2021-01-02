package com.manage.station.web.controller;

import com.manage.station.entity.DeviceEntity;
import com.manage.station.entity.StationEntity;
import com.manage.station.entity.UserEntity;
import com.manage.station.entity.UserStationEntity;
import com.manage.station.entity.id.UserStationId;
import com.manage.station.service.UserService;
import com.manage.station.service.impl.UserAuthenticatorServiceImpl;
import com.manage.station.web.controller.request.UserStationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserAuthenticatorServiceImpl authenticatorService;

    public UserController(UserService userService, UserAuthenticatorServiceImpl authenticatorService) {
        this.userService = userService;
        this.authenticatorService = authenticatorService;
    }



    @GetMapping("/monitor")
    String manageUser(Model model) {

        return "user/monitor";
    }

    //MANAGE STATION START
    @GetMapping("/manage-station")
    String manageRequest(Model model) {
        String username = authenticatorService.getUsernameLogin();
        List<UserStationEntity> listStationOfUser = userService.findAllStationByUser(username);
        model.addAttribute("listStationOfUser", listStationOfUser);
        return "user/manage-station";
    }

    @RequestMapping(value = "/manage-station/new", method = RequestMethod.POST)
    String addNewUserStation(UserStationForm form) {
        String username = authenticatorService.getUsernameLogin();
        UserStationEntity userStationEntity = UserStationEntity.builder()
                .id(null)
                .station(StationEntity.builder()
                        .id(null)
                        .activeFlag(true)
                        .deleteFlag(false)
                        .name(form.getName().trim())
                        .address(form.getAddress().trim())
                        .description(form.getDescription().trim())
                        .build())
                .user(UserEntity.builder().username(username).build())
                .build();
        userService.saveOrUpdateUserStation(userStationEntity);
        return "redirect:/user/manage-station";
    }

    @RequestMapping(value = "/manage-station/update", method = RequestMethod.POST)
    String updateExamination(UserStationForm form) {
        UserStationEntity userStationEntity = UserStationEntity.builder()
                .id(UserStationId.builder().userId(form.getUserId()).stationId(form.getStationId()).build())
                .station(StationEntity.builder()
                        .id(form.getStationId())
                        .activeFlag(true)
                        .deleteFlag(false)
                        .name(form.getName().trim())
                        .address(form.getAddress().trim())
                        .description(form.getDescription().trim())
                        .build())
                .user(UserEntity.builder().id(form.getUserId()).username(form.getUsername()).build())
                .build();
        userService.saveOrUpdateUserStation(userStationEntity);
        return "redirect:/user/manage-station";
    }

    @RequestMapping(value = "/manage-station/{stationId}", method = RequestMethod.GET)
    @ResponseBody
    public UserStationEntity findUserStationById(@PathVariable("stationId") Long stationId) {
        String username = authenticatorService.getUsernameLogin();
        UserEntity userEntity = userService.findUserByUsername(username);
        return userService.findByUserIdAndStationId(userEntity.getId(), stationId);
    }

    @RequestMapping(value = "/manage-station/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteExamination(Long stationId) {
        String username = authenticatorService.getUsernameLogin();
        UserEntity userEntity = userService.findUserByUsername(username);
        userService.deleteStationOfUser(userEntity.getId(), stationId);
        return "redirect:/user/manage-station";
    }

    //MANAGE STATION END
    @RequestMapping(value = "/manage-station/devices/{stationId}", method = RequestMethod.GET)
    public String getAllDeviceOfStationByUser(@PathVariable("stationId") Long stationId, Model model) {
        StationEntity stationEntity = userService.findStationById(stationId);
        List<DeviceEntity> listStationOfUser = userService.findAllDeviceByStationIdOfUser(stationId);
        model.addAttribute("listDevices", listStationOfUser);
        model.addAttribute("station", stationEntity);
        model.addAttribute("stationName", stationEntity.getName());
        return "user/station-devices";
    }
    //MANAGE DEVICE START

    //MANAGE DEVICE END

    @GetMapping("/report")
    String report(Model model) {

        return "user/report";
    }

    @GetMapping("/statistic")
    String statistic(Model model) {

        return "user/test";
    }

}