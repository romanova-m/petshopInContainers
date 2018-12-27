package mirea.auth;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final String secretKey = "SECRET";

    private AuthRepository authRepo;

    @Autowired
    public AuthService(AuthRepository authRepo){
        this.authRepo = authRepo;
    }

    @PostConstruct
    private void init()
    {
        authRepo.save(new User("admin","password","admin"));
    }

    public String getToken(User auth)
    {
        User user = getUserByLogin(auth.getLogin());
        if (user != null && user.getPassword().equals(auth.getPassword())){
            String payload = Base64.getEncoder().encodeToString(
                    ("" + user.getId() + " " + user.getRole()).getBytes());
            String signature = Base64.getEncoder().encodeToString(
                    (DigestUtils.sha256Hex(payload + secretKey)).getBytes());
            return payload + "." + signature;
        }
        return null;
    }

    private User getUserByLogin(String login) {
        Iterable<User> users = authRepo.findAll();
        for (User user: users)
        {
            if (user.getLogin().equals(login)) return user;
        }
        return null;
    }
}
