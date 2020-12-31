package com.manage.station.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "station")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//Trạm đo
public class StationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(255)", nullable = false)
    private String name; //tên

    @Column(name = "address", columnDefinition = "nvarchar(255)")
    private String address;     //Dia chi

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description; //mô tả

    @Column(name = "expiry_date")
    private Date expiryDate; //ngày hết hạn, giá trị khởi tạo = ngày duyệt yêu cầu + 1 năm

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag; //mặc định là false

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag; //mặc định là false

}
