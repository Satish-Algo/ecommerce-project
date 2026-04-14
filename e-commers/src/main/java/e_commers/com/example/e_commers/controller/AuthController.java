package e_commers.com.example.e_commers.controller;

import e_commers.com.example.e_commers.dto.requset.LoginRequest;
import e_commers.com.example.e_commers.dto.requset.SignupRequest;
import e_commers.com.example.e_commers.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return  authService.login(request);
    };
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

}
