package e_commerce.com.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private  String name;
    private String email;
    private String role ;
}
