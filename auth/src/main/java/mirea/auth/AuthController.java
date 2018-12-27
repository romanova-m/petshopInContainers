package mirea.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping(value ="auth", method = RequestMethod.POST)
    @ResponseBody
    public String getToken(@RequestBody User user){
        return authService.getToken(user);
    }
}
