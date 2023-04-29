package backend.hobbiebackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAvatarDto {
    private String avatar;
    private String avatarName;
}
