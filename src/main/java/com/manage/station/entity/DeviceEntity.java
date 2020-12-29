package com.manage.station.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//thiết bị đo
public class DeviceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stationId")
    private StationEntity station; //trạm đo

    @Column(name = "name", columnDefinition = "nvarchar(255)", nullable = false)
    private String name; //tên

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag;

    @Column(name = "unit", nullable = false)
    private String unit; //đơn vị đo

    @Column(name = "low_value", nullable = false)
    private Double lowValue; //ngưỡng thấp

    @Column(name = "high_value", nullable = false)
    private Double highValue; //ngưỡng cao
}
