package tictoc.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.profile.model.MoneyHistory;

public interface MoneyHistoryRepository extends JpaRepository<MoneyHistory, Long> {
}
