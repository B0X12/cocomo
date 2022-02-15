package cocomo.restserver.auth;

import cocomo.restserver.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthUserDaoService {

    private static List<AuthUser> authUsers = new ArrayList<>();
    private static int usersCount = 2000;

    static {
        // DB 확인 위해 임의로 넣어둔 데이터
        authUsers.add(new AuthUser(0, null));
        authUsers.add(new AuthUser(1, null));
    }

    public AuthUser save(AuthUser user)
    {
        if (user.getId() == null)
        {
            user.setId(++usersCount);
        }
        authUsers.add(user);
        return user;
    }

    public List<AuthUser> findAll()
    {
        return authUsers;
    }

    public AuthUser findById(int id)
    {
        for (AuthUser authUser : authUsers)
        {
            if (authUser.getId() == id)
            {
                return authUser;
            }
        }
        return null;
    }

}
