package backend.hobbiebackend.dto;

import backend.hobbiebackend.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppClientSignUpDto {
    private String username;
    private String fullName;
    private GenderEnum gender;
    private String email;
    private String password;

}
