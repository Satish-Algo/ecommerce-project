package e_commerce.com.auth.service;

import e_commerce.com.auth.dto.LoginRequest;
import e_commerce.com.auth.dto.LoginResponse;

public interface AuthService {
     LoginResponse login (LoginRequest request );
}
