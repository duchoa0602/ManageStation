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
public class UserStationForm implements Serializable {

    private String name; //tên

    private String address;     //Dia chi

    private String description; //mô tả

    private Long userId;
    private Long stationId;
    private String username;
}
