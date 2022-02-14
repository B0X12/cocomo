package cocomo.restserver.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // JpaRepository를 상속받았기 때문에
    // CRUD를 사용할 수 있음
}
