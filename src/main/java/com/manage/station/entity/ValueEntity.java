package com.manage.station.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "value")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//giá trị nhận được
public class ValueEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("deviceId")
    private DeviceEntity device; //thiết bị đo

    @Column(name = "val", nullable = false)
    private Double val; //giá trị đo: mặc định 3 chữ số sau dấu phẩy

    @Column(name = "created_date", nullable = false)
    private Date createdDate;
}
