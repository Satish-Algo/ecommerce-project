package e_commerce.com.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank
    private  String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
