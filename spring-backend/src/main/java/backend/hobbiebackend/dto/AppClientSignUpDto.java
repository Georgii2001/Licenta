package backend.hobbiebackend.dto;

import backend.hobbiebackend.enums.GenderEnum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppClientSignUpDto {
    private String username;
    private String fullName;
    private GenderEnum gender;
    private String email;
    private String password;
    private MultipartFile avatar;
}
