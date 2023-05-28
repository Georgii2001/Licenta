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

    Integer id;
    String username;
    GenderEnum gender;
    String email;
    String avatarFile;
    String role;
    String description;
    Long userMatchCount;
    List<String> interests;
    List<AvatarsDTO> avatarFiles;
}
