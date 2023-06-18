package travel.dto;

import travel.enums.GenderEnum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppClientSignUpDto {
    private String username;
    private String displayName;
    private GenderEnum gender;
    private String email;
    private String password;
    private MultipartFile avatar;
}
