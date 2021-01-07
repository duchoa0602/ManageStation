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
public class DeviceStationForm implements Serializable {
    private Long id;

    private Long stationId;

    private String name; //tên

    private Boolean activeFlag;

    private Boolean deleteFlag;

    private String unit; //đơn vị đo

    private Double lowValue; //ngưỡng thấp

    private Double highValue; //ngưỡng cao
}
