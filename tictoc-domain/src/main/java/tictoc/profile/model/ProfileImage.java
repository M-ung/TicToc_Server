package tictoc.profile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tictoc.model.tictoc.TicTocStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile_image")
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long profileId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private TicTocStatus status;

    public static ProfileImage of(Long profileId, String imageUrl) {
        return ProfileImage.builder()
                .profileId(profileId)
                .imageUrl(imageUrl)
                .status(TicTocStatus.ACTIVE)
                .build();
    }
}