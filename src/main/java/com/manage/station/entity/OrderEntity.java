package com.manage.station.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "order_")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//yêu cầu
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private Long code; //id của đối tượng cần xử lý, VD: stationId, deviceId

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserEntity user; //user yêu cầu xử lý

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("priceId")
    private PriceEntity price; //chi tiết xử lý

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description; //mô tả:nhập text (nếu có)

    @Column(name = "status")
    private Boolean status; //true: đã xử lý, mặc định là false: chưa xử lý

    @Column(name = "created_date", nullable = false)
    private Date createdDate; //ngày tạo yêu cầu

    @Column(name = "handled_date")
    private Date handledDate; //ngày xử lý: lấy new Date() lúc duyệt

    @Column(name = "handler_id")
    private Long handlerId; //userId người xử lý
}
