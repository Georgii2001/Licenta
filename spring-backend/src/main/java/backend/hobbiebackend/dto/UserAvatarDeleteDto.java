package backend.hobbiebackend.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAvatarDeleteDto {
    private MultipartFile avatar;
    private String avatarName;
}
