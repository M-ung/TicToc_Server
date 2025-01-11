package org.tictoc.tictoc.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.domain.user.entity.type.UserRole;
import org.tictoc.tictoc.global.common.entity.BaseTimeEntity;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kakaoId;
    private String name;
    private UserRole role;
    private TicTocStatus status;
}
