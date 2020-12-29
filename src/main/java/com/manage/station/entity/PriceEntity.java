package com.manage.station.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "price")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//bảng giá: hiện tại để mặc định chỉ có các loại bản ghi: regisDevice: 200.000, regisStation: 500.000, extendStation: 200.000, delete* = 0.0
public class PriceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_type", nullable = false)
    private String orderType; //loại yêu cầu (mô tả trong class const)

    @Column(name = "object_type", nullable = false)
    private String objectType; //loại đối tượng (mô tả trong class const)

    @Column(name = "price", nullable = false)
    private Double price; //giá

    @Column(name = "unit", nullable = false)
    private String unit; //đơn vị đo mặc định là VND
}
