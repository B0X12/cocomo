package cocomo.restserver.user;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int usersCount = 0; // 임의로 넣어둔 데이터가 3까지

    /*
    static {
        // DB 확인 위해 임의로 넣어둔 데이터
        users.add(new User(++usersCount, "1234", "coco", "coco@gmail.com" ,new Date()));
        users.add(new User(++usersCount, "abcd", "moco", "mococoococo@gmail.com" ,new Date()));
    }
    */

    public User save(User user)
    {
        if (user.getId() == null)
        {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public List<User> findAll()
    {
        return users;
    }

    public User findOne(int id)
    {
        for (User user : users)
        {
            if (user.getId() == id)
            {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id)
    {
        // 열거형 데이터 관련 자료형
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext())
        {
            User user = iterator.next(); // 뒤로 가면서

            if (user.getId() == id) // id를 찾음
            {
               iterator.remove();
               return user;
            }
        }
        return null;
    }
}

