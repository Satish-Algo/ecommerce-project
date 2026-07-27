package e_commerce.com.user.service;

import e_commerce.com.user.dto.UserRequest;
import e_commerce.com.user.dto.UserResponse;


public interface UserService {
     UserResponse createUser( UserRequest request );
}
