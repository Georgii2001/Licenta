package backend.hobbiebackend.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAvatarDto {

    private Integer id;
    private MultipartFile avatar;
}
