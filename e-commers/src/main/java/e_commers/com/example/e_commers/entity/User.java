package e_commers.com.example.e_commers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "user")
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String  email;
    private String password;
    private String role;


}
