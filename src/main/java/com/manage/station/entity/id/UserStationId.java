package com.manage.station.entity.id;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserStationId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "station_id")
    private Long stationId;
}
