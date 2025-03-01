package tictoc.user.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import tictoc.user.model.UserLoginHistory;

@Repository
public interface UserLoginHistoryRepository extends ElasticsearchRepository<UserLoginHistory, Long> {
}