package tictoc.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tictoc.profile.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByUserIdAndMoneyGreaterThanEqual(Long userId, Integer price);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Profile p SET p.money = p.money - :price WHERE p.userId = :userId AND p.money >= :price")
    int subtractMoney(@Param("userId") Long userId, @Param("price") Integer price);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Profile p SET p.money = p.money + :price WHERE p.userId = :userId")
    int addMoney(@Param("userId") Long userId, @Param("price") Integer price);
}
