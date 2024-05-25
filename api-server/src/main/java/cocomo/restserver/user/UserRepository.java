package cocomo.restserver.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // JpaRepository를 상속받았기 때문에 CRUD를 사용할 수 있음

    Optional<User> findByUserId(String userId);
}
