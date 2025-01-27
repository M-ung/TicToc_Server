package org.tictoc.tictoc.domain.profile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.global.common.entity.base.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String nickname;
    private Integer money;
}