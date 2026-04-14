package e_commers.com.example.e_commers.service;

import e_commers.com.example.e_commers.dto.requset.LoginRequest;
import e_commers.com.example.e_commers.dto.requset.SignupRequest;
import e_commers.com.example.e_commers.entity.User;
import e_commers.com.example.e_commers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public String login(LoginRequest loginRequest) {
         User user = userRepository.findByEmail(loginRequest.getEmail())
                 .orElseThrow( ()-> new RuntimeException("User not found!"));
          if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
              throw new RuntimeException("Wrong password!");
          }

        return " login success";

    }
    public String signup(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return "Email already in use";
        }
         User user =  new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return "success";
    }
}
