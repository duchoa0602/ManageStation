package com.manage.station.web.controller;

import com.manage.mqtt.client.MqttClientController;
import com.manage.station.entity.*;
import com.manage.station.entity.id.UserStationId;
import com.manage.station.service.UserService;
import com.manage.station.service.impl.UserAuthenticatorServiceImpl;
import com.manage.station.web.controller.highchart.*;
import com.manage.station.web.controller.request.DeviceStationForm;
import com.manage.station.web.controller.request.UserStationForm;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
    String manageUser(HttpServletRequest httpRequest, Model model) {

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
    String updateStation(UserStationForm form) {
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

    @RequestMapping(value = "/manage-station/station/{stationId}", method = RequestMethod.GET)
    @ResponseBody
    public UserStationEntity findUserStationById(@PathVariable("stationId") Long stationId) {
        String username = authenticatorService.getUsernameLogin();
        UserEntity userEntity = userService.findUserByUsername(username);
        return userService.findByUserIdAndStationId(userEntity.getId(), stationId);
    }

    @RequestMapping(value = "/manage-station/station/{stationId}/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteStation(@PathVariable("stationId") Long stationId) {
        String username = authenticatorService.getUsernameLogin();
        UserEntity userEntity = userService.findUserByUsername(username);
        userService.deleteStationOfUser(userEntity.getId(), stationId);
        return "redirect:/user/manage-station";
    }

    //MANAGE STATION END
    @RequestMapping(value = "/manage-station/station/{stationId}/devices", method = RequestMethod.GET)
    public String getAllDeviceOfStationByUser(@PathVariable("stationId") Long stationId, Model model) {
        StationEntity stationEntity = userService.findStationById(stationId);
        List<DeviceEntity> listStationOfUser = userService.findAllDeviceByStationIdOfUser(stationId);
        model.addAttribute("listDevices", listStationOfUser);
        model.addAttribute("station", stationEntity);
        model.addAttribute("stationId", stationId);
        return "user/station-devices";
    }
    //MANAGE DEVICE START
    @RequestMapping(value = "/manage-station/station/{stationId}/devices/new", method = RequestMethod.POST)
    String addNewDeviceStation(@PathVariable("stationId") Long stationId, DeviceStationForm form) {
        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(null)
                .activeFlag(true)
                .deleteFlag(false)
                .name(form.getName().trim())
                .unit(form.getUnit().trim())
                .lowValue(form.getLowValue())
                .highValue(form.getHighValue())
                .station(StationEntity.builder().id(stationId).build())
                .build();
        userService.saveOrUpdateDeviceStation(deviceEntity);
        return "redirect:/user/manage-station/station/" + stationId + "/devices";
    }

    @RequestMapping(value = "/manage-station/station/{stationId}/devices/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public DeviceEntity findDeviceById(@PathVariable("stationId") Long stationId,
                                       @PathVariable("deviceId") Long deviceId) {
        return userService.findDeviceById(deviceId);
    }

    @RequestMapping(value = "/manage-station/station/devices/update", method = RequestMethod.POST)
    String updateDeviceStation(DeviceStationForm form) {
        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(form.getId())
                .deleteFlag(true)
                .activeFlag(false)
                .name(form.getName().trim())
                .unit(form.getUnit().trim())
                .lowValue(form.getLowValue())
                .highValue(form.getHighValue())
                .station(StationEntity.builder().id(form.getStationId()).build())
                .build();
        userService.saveOrUpdateDeviceStation(deviceEntity);
        return "redirect:/user/manage-station/station/" + form.getStationId() + "/devices";
    }

    @RequestMapping(value = "/manage-station/station/{stationId}/devices/{deviceId}/delete",
            method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteDeviceStation(@PathVariable("stationId") Long stationId,
                                      @PathVariable("deviceId") Long deviceId) {
        userService.deleteDeviceById(deviceId);
        return "redirect:/user/manage-station/station/" + stationId + "/devices";
    }
    //MANAGE DEVICE END

    @GetMapping("/report")
    String report(Model model) {

        return "user/report";
    }

    @GetMapping("/statistic")
    String statistic(Model model) {

        return "user/statistic";
    }

    @ResponseBody
    @RequestMapping(value = "/monitor/{stationId}/{timeType}", method = RequestMethod.GET)
    public List<HighChartModel> monitor(@PathVariable("stationId") Long stationId, @PathVariable("timeType") String timeType){
        SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        List<HighChartModel> highCharts = new ArrayList<HighChartModel>();
        Date fromDate = null;
        Date toDate = null;
        String time = "";
        switch(timeType) {
            case "day":
                toDate = new Date(new java.util.Date().getTime());
                fromDate = new Date(toDate.getTime() - 86400000L);
                time = " 24h trước " + fm.format(toDate);
                break;
            case "month":
                toDate = new Date(new java.util.Date().getTime());
                fromDate = new Date(toDate.getTime() - 86400000L*30);
                time = " 30 ngày trước " + fm.format(toDate).substring(9, 19);
                break;
            case "year":
                toDate = new Date(new java.util.Date().getTime());
                fromDate = new Date(toDate.getTime() - 86400000L*365);
                time = " 1 năm trước " + fm.format(toDate).substring(9, 19);
                break;
        }
        List<DeviceEntity> listDevice = userService.findAllDeviceByStationIdOfUser(stationId);
        for (DeviceEntity device : listDevice) {
            HighChartModel highChartModel = new HighChartModel();
            Title title = new Title(device.getName() + time);
            XAxis xAxis = new XAxis();
            YAxis yAxis = new YAxis();
            yAxis.setMin("0");
            yAxis.setTitle(new Title(device.getUnit()));
            List<SeriesObj> seriesObjs = new ArrayList<SeriesObj>();
            SeriesObj seriesObj = new SeriesObj();
            seriesObj.setName(device.getName());
            List<String> xString = new ArrayList<String>();
            List<String> yString = new ArrayList<String>();

            List<ValueEntity> valueEntityList = userService.getValueByDeviceIdAndTime(device.getId(), fromDate, toDate);
            for (ValueEntity valueEntity : valueEntityList) {
                xString.add(String.valueOf(valueEntity.getVal()));
                switch(timeType) {
                    case "day":
                        yString.add(fm.format(valueEntity.getCreatedDate()).substring(0,8));
                        break;
                    default:
                        yString.add(fm.format(valueEntity.getCreatedDate()));
                }
            }

            xAxis.setCategories(xString);
            seriesObj.setData(yString);
            seriesObjs.add(seriesObj);
            highChartModel.setSeries(seriesObjs);
            highChartModel.setyAxis(yAxis);
            highChartModel.setxAxis(xAxis);
            highChartModel.setTitle(title);
            highCharts.add(highChartModel);
        }
        return highCharts;
    }

    @ResponseBody
    @RequestMapping(value = "/control/{deviceId}/{status}", method = RequestMethod.GET)
    public int control(@PathVariable("deviceId") Long deviceId, @PathVariable("status") int status){
        int check = 400;
        if (!MqttClientController.checkConnect){
            return check;
        }
        DeviceEntity deviceEntity = userService.getDeviceById(deviceId);
        if (deviceEntity != null){
            String mess = "{\"deviceId\":\"" + deviceId + "\",\"status\":\"" + status + "\"}";
            MqttClientController.publish(mess,MqttClientController.topicPud);
            check = 200;
        }
        return check;
    }

}