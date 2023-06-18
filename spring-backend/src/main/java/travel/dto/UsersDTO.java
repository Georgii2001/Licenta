package travel.dto;

import travel.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UsersDTO {

    private Integer id;
    private String username;
    private String displayName;
    private GenderEnum gender;
    private String email;
    private String avatarFile;
    private String role;
    private String description;
    private Long userMatchCount;
    private List<String> interests;
    private List<AvatarsDTO> avatarFiles;
}
