package org.tictoc.tictoc.domain.auction.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Zone { //TODO 지역 관련 공공 데이터 OPEN API 가져올 예정
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String localNameOfCity;

    private String province;

    public static Zone map(String line) {
        String[] split = line.split(",");
        return Zone.builder()
                .city(split[0])
                .localNameOfCity(split[1])
                .province(split[2])
                .build();
    }
}