package com.manage.station.entity;

import com.manage.station.entity.id.UserStationId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_station")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserStationEntity  implements Serializable {
    @EmbeddedId
    private UserStationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stationId")
    private StationEntity station;
}
