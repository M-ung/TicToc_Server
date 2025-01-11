package org.tictoc.tictoc.domain.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long profileId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageUrl;
    private TicTocStatus status;
}
